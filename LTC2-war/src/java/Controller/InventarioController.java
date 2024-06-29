/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Bitacora;
import Entity.Inventario;
import Entity.OrdenAccesorio;
import Facade.InventarioFacade;
import Facade.OrdenAccesorioFacade;
import Facade.OrdenFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import java.util.Base64;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author lenovo
 */
@Named(value = "inventarioController")
@SessionScoped
public class InventarioController implements Serializable {

    @EJB
    private InventarioFacade inventarioFacade;

    @EJB
    private OrdenFacade ordenFacade;

    @EJB
    private OrdenAccesorioFacade ordenAccesorioFacade;

    @Inject
    private DatosAccesorioController datosAccesorioController;

    @Inject
    private OrdenAccesorioController ordenAccesorioController;

    @Inject
    private OrdenController ordenController;

    @Inject
    private BitacoraController bitacoraController;

    private Inventario inventario = new Inventario();
    private List<Inventario> productosInventarioSeleccionados;

    //Constructor
    public InventarioController() {
    }

    public void onChangeCheck() {
        // Lógica para manejar el cambio en el estado del checkbox
    }

    @PostConstruct
    void init() {
        productosInventarioSeleccionados = new ArrayList<>();

    }

    public String agregarProductoEstiloExistente(int idOrdenAccesorio, int cantidadAgregar) {
        FacesMessage msj;
        int cantidadEnStock = 0;
        Inventario inventarioExistente = new Inventario();
        try {
            if (idOrdenAccesorio > 0) {
                if (cantidadAgregar > 0) {
                    inventarioExistente = inventarioFacade.consultarProductoPorOrdenAccesorio(idOrdenAccesorio);

                    if (inventarioExistente != null) {
                        cantidadEnStock = cantidadAgregar + inventarioExistente.getEnStock();
                        actualizarStock(inventarioExistente.getIdInventario(), cantidadEnStock);
                        msj = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se han añadido los artículos correctamente.", "");
                        FacesContext.getCurrentInstance().addMessage("AgregarProductoEstiloExistente", msj);
                    }
                } else {
                    msj = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se ha agregado la cantidad de productos correctamente, intentelo nuevamente.", "");
                    FacesContext.getCurrentInstance().addMessage("AgregarProductoEstiloExistente", msj);
                }
            }

        } catch (Exception e) {

        }
        return "";
    }

    public void actualizarStock(int idInventario, int nuevoStock) {
        inventarioFacade.actualizarStock(idInventario, nuevoStock);
    }

    public String guardarNuevoEstilo(Inventario inventario) {

        FacesMessage msj;

        String nombreSinEspacios = inventario.getOrdenAccesorio()
                .getDatosAccesorio().getNombre().trim();

        String categoriaSinEspacios = inventario.getOrdenAccesorio()
                .getDatosAccesorio().getCategoria().getNombre().trim();

        String clasificacionSinEspacios = inventario.getOrdenAccesorio()
                .getDatosAccesorio().getClasificacion().getNombre().trim();

        //Valida los campos requeridos
        if (!nombreSinEspacios.isEmpty()
                && !categoriaSinEspacios.isEmpty()
                && !clasificacionSinEspacios.isEmpty()
                && inventario.getOrdenAccesorio().getDatosAccesorio().getImagen() != null
                && inventario.getEnStock() > 0
                && inventario.getOrdenAccesorio().getPrecioInicial() > 0) {

            int precioSugerido = ordenAccesorioController.calcularPrecioSugerido(
                    inventario.getOrdenAccesorio().getPrecioInicial());

            //AGREGA LA ORDEN DEL NUEVO ESTILO DEL PRODUCTO
            int idOrden = ordenController.insert(inventario.getOrdenAccesorio().getOrden());
            if (idOrden > 0) {
                inventario.getOrdenAccesorio().setPrecioSugerido(precioSugerido);
                inventario.getOrdenAccesorio().setIdOrdenAccesorio(idOrden);

                //AGREGA LOS DATOS ACCESORIO DEL NUEVO ESTILO DEL PRODUCTO
                int idDatosAccesorio = datosAccesorioController.insert(inventario.getOrdenAccesorio().getDatosAccesorio());
                inventario.getOrdenAccesorio().getDatosAccesorio().setIdDatosAccesorio(idDatosAccesorio);

                //AGREGA EL PROVEEDOR DEL NUEVO ESTILO DEL PRODUCTO
                int idOrdenAccesorio = ordenAccesorioController.insert(inventario.getOrdenAccesorio());
                inventario.getOrdenAccesorio().setIdOrdenAccesorio(idOrdenAccesorio);

                //AGREGA EL NUEVO ESTILO DEL PRODUCTO AL INVENTARIO
                if (idOrdenAccesorio > 0) {
                    inventarioFacade.insert(inventario);
                    Bitacora bitacora = new Bitacora();
                    bitacora = llenarBitacora(inventario);
                    bitacoraController.insert(bitacora);
                }
            }
        }
        return "";
    }

    //Actualiza el precio inicial, precio sugerido y proveedor sugerido
    public String corregirPrecioInicioProveedorSugerido(Inventario inventario) {
        FacesMessage msj;
        try {
            OrdenAccesorio ordenAccesorio = new OrdenAccesorio();
            ordenAccesorio = inventario.getOrdenAccesorio();

            //Valida que el precio incial sea mayor a 0
            if (ordenAccesorio.getPrecioInicial() > 0) {

                //Calcula y actualiza el precio sugerido en el proveedor sugerido actual
                int precioSugerido = ordenAccesorioController
                        .calcularPrecioSugerido(ordenAccesorio.getPrecioInicial());

                ordenAccesorioFacade.actualizarPrecioInicio(
                        ordenAccesorio.getPrecioInicial(),
                        ordenAccesorio.getIdOrdenAccesorio(),
                        precioSugerido);

                //Valida si sigue siendo la mejor opción de proveedor registrado 
                //para ser el proveedor sugerido del producto y de ser así lo 
                //actualiza por otro ya registrado.
                OrdenAccesorio ordenAccesorioExistente = new OrdenAccesorio();
                ordenAccesorioExistente = ordenAccesorioFacade
                        .consultarPorDatosAccesorioProveedorSugerido(
                                ordenAccesorio.getDatosAccesorio()
                                        .getIdDatosAccesorio());

                if (!ordenAccesorioExistente.getIdOrdenAccesorio()
                        .equals(ordenAccesorio.getIdOrdenAccesorio())) {

                    //Actualiza el proveedor sugerido    
                    editarOrdenAccesorio(inventario.getIdInventario(),
                            ordenAccesorioExistente.getIdOrdenAccesorio());
                    msj = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "La información del proveedor " + ordenAccesorio.getOrden().getOrden()
                            + "/" + ordenAccesorio.getOrden().getSku()
                            + " fue corregida correctamente y se ha seleccionado un nuevo proveedor sugerido: "
                            + ordenAccesorioExistente.getOrden().getOrden()
                            + "/" + ordenAccesorioExistente.getOrden().getSku() + ".", "");
                    FacesContext.getCurrentInstance().addMessage("EditarOrden", msj);

                } else {
                    msj = new FacesMessage(FacesMessage.SEVERITY_INFO, "La orden se ha corregido correctamente.", "");
                    FacesContext.getCurrentInstance().addMessage("EditarOrden", msj);
                }

            } else {
                msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La orden no pudo ser corregida, consulte con el administrador", "");
                FacesContext.getCurrentInstance().addMessage("EditarOrden", msj);
            }
        } catch (Exception e) {

        }
        return "";
    }

    //Actualiza el precio inicial, precio sugerido y proveedor sugerido
    public String updatePrecioInicio(Inventario inventario) {
        FacesMessage msj;
        try {
            OrdenAccesorio ordenAccesorio = new OrdenAccesorio();
            ordenAccesorio = inventario.getOrdenAccesorio();

            //Valida que el precio incial sea mayor a 0
            if (ordenAccesorio.getPrecioInicial() > 0) {

                //Calcula y actualiza el precio sugerido en el proveedor sugerido actual
                int precioSugerido = ordenAccesorioController
                        .calcularPrecioSugerido(ordenAccesorio.getPrecioInicial());

                ordenAccesorioFacade.actualizarPrecioInicio(
                        ordenAccesorio.getPrecioInicial(),
                        ordenAccesorio.getIdOrdenAccesorio(),
                        precioSugerido);

                //Valida si sigue siendo la mejor opción de proveedor registrado 
                //para ser el proveedor sugerido del producto y de ser así lo 
                //actualiza por otro ya registrado.
                OrdenAccesorio ordenAccesorioExistente = new OrdenAccesorio();
                ordenAccesorioExistente = ordenAccesorioFacade
                        .consultarPorDatosAccesorioProveedorSugerido(
                                ordenAccesorio.getDatosAccesorio()
                                        .getIdDatosAccesorio());

                if (!ordenAccesorioExistente.getIdOrdenAccesorio()
                        .equals(ordenAccesorio.getIdOrdenAccesorio())) {

                    //Actualiza el proveedor sugerido    
                    editarOrdenAccesorio(inventario.getIdInventario(),
                            ordenAccesorioExistente.getIdOrdenAccesorio());
                    msj = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "La información del proveedor " + ordenAccesorio.getOrden().getOrden()
                            + "/" + ordenAccesorio.getOrden().getSku()
                            + " fue corregida correctamente y se ha seleccionado un nuevo proveedor sugerido: "
                            + ordenAccesorioExistente.getOrden().getOrden()
                            + "/" + ordenAccesorioExistente.getOrden().getSku() + ".", "");
                    FacesContext.getCurrentInstance().addMessage("EditarOrden", msj);

                } else {
                    msj = new FacesMessage(FacesMessage.SEVERITY_INFO, "La orden se ha corregido correctamente.", "");
                    FacesContext.getCurrentInstance().addMessage("EditarOrden", msj);
                }

            } else {
                msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La orden no pudo ser corregida, consulte con el administrador", "");
                FacesContext.getCurrentInstance().addMessage("EditarOrden", msj);
            }
        } catch (Exception e) {

        }
        return "";
    }

    public void editarOrdenAccesorio(int idInventario, int idOrdenAccesorio) {
        try {
            inventarioFacade.editarOrdenAccesorio(idInventario, idOrdenAccesorio);
        } catch (Exception e) {
        }
    }

    public void descargarExcel(List<Inventario> inventarioList) {
        // Crear un nuevo libro de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        // Estilo para las cabeceras en negrita
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Estilo para centrar el texto en las celdas
        CellStyle centerAlignmentStyle = workbook.createCellStyle();
        centerAlignmentStyle.setAlignment(HorizontalAlignment.CENTER);

        // Establecer el alto de las filas
        sheet.setDefaultRowHeight((short) (40 * 40)); // 30 puntos

        // Crear la fila de encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Imagen");
        headerRow.createCell(1).setCellValue("Categoría");
        headerRow.createCell(2).setCellValue("Tipo");
        headerRow.createCell(3).setCellValue("Nombre");
        headerRow.createCell(4).setCellValue("Orden");
        headerRow.createCell(5).setCellValue("SKU");
        headerRow.createCell(6).setCellValue("Stock");
        headerRow.createCell(7).setCellValue("Precio Sugerido");

        // Aplicar estilo a las cabeceras
        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        // Llenar el contenido del Excel con los datos del inventario
        int rowNum = 1;
        for (Inventario inventario : inventarioList) {
            Row row = sheet.createRow(rowNum++);

            // Obtener la imagen en formato Base64
            String imagenBase64 = getImagenBase64(inventario);
            // Agregar la imagen al Excel
            if (imagenBase64 != null && !imagenBase64.isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(imagenBase64.split(",")[1]);
                int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
                CreationHelper helper = workbook.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(0);
                anchor.setRow1(rowNum - 1);
                anchor.setCol2(1);
                anchor.setRow2(rowNum);
                Picture picture = drawing.createPicture(anchor, pictureIdx);
                // Ajustar el tamaño de la imagen (puedes modificar este valor según tus necesidades)
                picture.resize(1, 1); // Aumentar tamaño en 3 veces
            }

            // Agregar los demás datos al Excel y aplicar estilo de centrado
            Cell categoriaCell = row.createCell(1);
            categoriaCell.setCellValue(inventario.getOrdenAccesorio().getDatosAccesorio().getCategoria().getNombre());
            categoriaCell.setCellStyle(centerAlignmentStyle);

            Cell tipoCell = row.createCell(2);
            tipoCell.setCellValue(inventario.getOrdenAccesorio().getDatosAccesorio().getClasificacion().getNombre());
            tipoCell.setCellStyle(centerAlignmentStyle);

            Cell nombreCell = row.createCell(3);
            nombreCell.setCellValue(inventario.getOrdenAccesorio().getDatosAccesorio().getNombre());
            nombreCell.setCellStyle(centerAlignmentStyle);

            Cell ordenCell = row.createCell(4);
            ordenCell.setCellValue(inventario.getOrdenAccesorio().getOrden().getOrden());
            ordenCell.setCellStyle(centerAlignmentStyle);

            Cell skuCell = row.createCell(5);
            skuCell.setCellValue(inventario.getOrdenAccesorio().getOrden().getSku());
            skuCell.setCellStyle(centerAlignmentStyle);

            Cell stockCell = row.createCell(6);
            stockCell.setCellValue(inventario.getEnStock());
            stockCell.setCellStyle(centerAlignmentStyle);

            Cell precioSugeridoCell = row.createCell(7);
            precioSugeridoCell.setCellValue(inventario.getOrdenAccesorio().getPrecioSugerido());
            precioSugeridoCell.setCellStyle(centerAlignmentStyle);
        }

        // Ajustar el tamaño de las columnas según el contenido
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Establecer un ancho fijo para la primera columna
        int firstColumnWidth = 10000; // Ancho en unidades de medida
        sheet.setColumnWidth(0, firstColumnWidth);

        // Ajustar el tamaño de las filas según el contenido
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                row.setHeight((short) -1); // Restaurar el tamaño predeterminado antes de ajustar
                // Código para ajustar el tamaño de la fila según el contenido
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        sheet.autoSizeColumn(j); // Ajustar el ancho de la columna
                    }
                }
            }
        }

        // Escribir el libro de Excel en un flujo de salida
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"inventario.xlsx\"");

            workbook.write(response.getOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Inventario> prepareListaProductosSeleccionados() {
        List<Integer> idsInt = new ArrayList<>();
        String[] idsCheckList = (String[]) FacesContext
                .getCurrentInstance().getExternalContext()
                .getSessionMap().get("checkList");

        if (idsCheckList.length > 0) {
            for (String id : idsCheckList) {
                idsInt.add(Integer.valueOf(id));
            }

            return consultaPorIdsInventario(idsInt);
        } else {
            return null;
        }
    }

    /*  public void prepareCantidadPiezasVendidas(int idInventario, int enStock, int piezasVendidas) {
        FacesMessage msj;
        int nuevoStock = 0;
        Inventario inv = new Inventario();
        inv.setIdInventario(idInventario);
        if (piezasVendidas > 0) {
            nuevoStock = enStock - piezasVendidas;
            inv.setEnStock(nuevoStock);
            getInventarioListCantidadVendida().add(inv);
        }
    }

    public void registrarVentas(List<Inventario> inventarioList) {
        for (Inventario i: inventarioList) {
            
        }
    }
     */
    public Inventario consultaPorIdInventario(int idInventario) {
        return inventarioFacade.findInventarioById(idInventario);
    }

    //Consulta productos seleccionados para piezas vendidas
    public List<Inventario> consultaPorIdsInventario(List<Integer> idInventarioList) {
        return inventarioFacade.consultaPorIdsInventario(idInventarioList);
    }

    public Bitacora llenarBitacora(Inventario inventario) {
        Bitacora bitacora = new Bitacora();

        bitacora.setCategoria(inventario.getOrdenAccesorio().getDatosAccesorio().getCategoria().getNombre());
        bitacora.setDescripcion(inventario.getOrdenAccesorio().getDatosAccesorio().getDescripcion());
        bitacora.setImagen(inventario.getOrdenAccesorio().getDatosAccesorio().getImagen());
        bitacora.setNombre(inventario.getOrdenAccesorio().getDatosAccesorio().getNombre());
        bitacora.setOrden(inventario.getOrdenAccesorio().getOrden().getOrden());
        bitacora.setSku(inventario.getOrdenAccesorio().getOrden().getSku());
        bitacora.setPrecio(String.valueOf(inventario.getOrdenAccesorio().getPrecioInicial()));
        bitacora.setEnStock(String.valueOf(inventario.getEnStock()));

        return bitacora;
    }

    public void reorganizaProveedorSugerido() {
        int idDatosAccesorio = this.inventario.getOrdenAccesorio()
                .getDatosAccesorio().getIdDatosAccesorio();
    }

    public String mainClean(String url) {
        this.inventario = new Inventario();
        return url;
    }

    public String prepareEdit(Inventario i) {
        this.inventario = i;
        return "EditarProveedorSugerido";
    }

    public String getImagenBase64(Inventario i) {
        if (i.getOrdenAccesorio().getDatosAccesorio().getImagen() != null) {
            byte[] imageData = i.getOrdenAccesorio().getDatosAccesorio().getImagen();
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return "";
    }

    //Getter y setter
    public InventarioFacade getInventarioFacade() {
        return inventarioFacade;
    }

    public void setInventarioFacade(InventarioFacade inventarioFacade) {
        this.inventarioFacade = inventarioFacade;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    //Metods
    public List<Inventario> findAll() {
        return getInventarioFacade().findAll();
    }

    public String insert(Inventario inventario) {
        try {
            inventarioFacade.insert(inventario);

        } catch (Exception e) {

        }
        return "";
    }

    /**
     * @return the ordenFacade
     */
    public OrdenFacade getOrdenFacade() {
        return ordenFacade;
    }

    /**
     * @param ordenFacade the ordenFacade to set
     */
    public void setOrdenFacade(OrdenFacade ordenFacade) {
        this.ordenFacade = ordenFacade;
    }

    /**
     * @return the ordenAccesorioFacade
     */
    public OrdenAccesorioFacade getOrdenAccesorioFacade() {
        return ordenAccesorioFacade;
    }

    /**
     * @param ordenAccesorioFacade the ordenAccesorioFacade to set
     */
    public void setOrdenAccesorioFacade(OrdenAccesorioFacade ordenAccesorioFacade) {
        this.ordenAccesorioFacade = ordenAccesorioFacade;
    }

    /**
     * @return the productosInventarioSeleccionados
     */
    public List<Inventario> getProductosInventarioSeleccionados() {
        return productosInventarioSeleccionados;
    }

    /**
     * @param productosInventarioSeleccionados the
     * productosInventarioSeleccionados to set
     */
    public void setProductosInventarioSeleccionados(List<Inventario> productosInventarioSeleccionados) {
        this.productosInventarioSeleccionados = productosInventarioSeleccionados;
    }

}
