/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Categoria;
import Entity.Clasificacion;
import Entity.ConfiguracionPrecio;
import Entity.DatosAccesorio;
import Estructuras.Columnas;
import Facade.DatosAccesorioFacade;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class DatosAccesorioControllerTest {
    
    public DatosAccesorioControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findAll method, of class DatosAccesorioController.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        DatosAccesorioController instance = new DatosAccesorioController();
        List<DatosAccesorio> expResult = null;
        List<DatosAccesorio> result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class DatosAccesorioController.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        DatosAccesorioController instance = new DatosAccesorioController();
        String expResult = "";
        String result = instance.insert();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllClasificaciones method, of class DatosAccesorioController.
     */
    @Test
    public void testFindAllClasificaciones() {
        System.out.println("findAllClasificaciones");
        DatosAccesorioController instance = new DatosAccesorioController();
        List<Clasificacion> expResult = null;
        List<Clasificacion> result = instance.findAllClasificaciones();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerCategorias method, of class DatosAccesorioController.
     */
    @Test
    public void testObtenerCategorias() {
        System.out.println("obtenerCategorias");
        Integer idClasificacion = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        List<Categoria> expResult = null;
        List<Categoria> result = instance.obtenerCategorias(idClasificacion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarEstilos method, of class DatosAccesorioController.
     */
    @Test
    public void testBuscarEstilos() {
        System.out.println("buscarEstilos");
        Integer idClasificacion = null;
        Integer idCategoria = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.buscarEstilos(idClasificacion, idCategoria);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarPorDatosAccesorio method, of class DatosAccesorioController.
     */
    @Test
    public void testConsultarPorDatosAccesorio() {
        System.out.println("consultarPorDatosAccesorio");
        DatosAccesorio da = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.consultarPorDatosAccesorio(da);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generarColumnas method, of class DatosAccesorioController.
     */
    @Test
    public void testGenerarColumnas() {
        System.out.println("generarColumnas");
        List<DatosAccesorio> listaDatosAccesorios = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        Object expResult = null;
        Object result = instance.generarColumnas(listaDatosAccesorios);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImagenBase64 method, of class DatosAccesorioController.
     */
    @Test
    public void testGetImagenBase64() {
        System.out.println("getImagenBase64");
        DatosAccesorio da = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        String expResult = "";
        String result = instance.getImagenBase64(da);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clean method, of class DatosAccesorioController.
     */
    @Test
    public void testClean() {
        System.out.println("clean");
        DatosAccesorioController instance = new DatosAccesorioController();
        String expResult = "";
        String result = instance.clean();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConfiguracionPrecio method, of class DatosAccesorioController.
     */
    @Test
    public void testGetConfiguracionPrecio() {
        System.out.println("getConfiguracionPrecio");
        DatosAccesorioController instance = new DatosAccesorioController();
        ConfiguracionPrecio expResult = null;
        ConfiguracionPrecio result = instance.getConfiguracionPrecio();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConfiguracionPrecio method, of class DatosAccesorioController.
     */
    @Test
    public void testSetConfiguracionPrecio() {
        System.out.println("setConfiguracionPrecio");
        ConfiguracionPrecio configuracionPrecio = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setConfiguracionPrecio(configuracionPrecio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCantidadStockAgregada method, of class DatosAccesorioController.
     */
    @Test
    public void testGetCantidadStockAgregada() {
        System.out.println("getCantidadStockAgregada");
        DatosAccesorioController instance = new DatosAccesorioController();
        Integer expResult = null;
        Integer result = instance.getCantidadStockAgregada();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCantidadStockAgregada method, of class DatosAccesorioController.
     */
    @Test
    public void testSetCantidadStockAgregada() {
        System.out.println("setCantidadStockAgregada");
        Integer cantidadStockAgregada = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setCantidadStockAgregada(cantidadStockAgregada);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCategoria method, of class DatosAccesorioController.
     */
    @Test
    public void testGetCategoria() {
        System.out.println("getCategoria");
        DatosAccesorioController instance = new DatosAccesorioController();
        Categoria expResult = null;
        Categoria result = instance.getCategoria();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCategoria method, of class DatosAccesorioController.
     */
    @Test
    public void testSetCategoria() {
        System.out.println("setCategoria");
        Categoria categoria = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setCategoria(categoria);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClasificacion method, of class DatosAccesorioController.
     */
    @Test
    public void testGetClasificacion() {
        System.out.println("getClasificacion");
        DatosAccesorioController instance = new DatosAccesorioController();
        Clasificacion expResult = null;
        Clasificacion result = instance.getClasificacion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setClasificacion method, of class DatosAccesorioController.
     */
    @Test
    public void testSetClasificacion() {
        System.out.println("setClasificacion");
        Clasificacion clasificacion = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setClasificacion(clasificacion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDatosAccesorioFacade method, of class DatosAccesorioController.
     */
    @Test
    public void testGetDatosAccesorioFacade() {
        System.out.println("getDatosAccesorioFacade");
        DatosAccesorioController instance = new DatosAccesorioController();
        DatosAccesorioFacade expResult = null;
        DatosAccesorioFacade result = instance.getDatosAccesorioFacade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDatosAccesorioFacade method, of class DatosAccesorioController.
     */
    @Test
    public void testSetDatosAccesorioFacade() {
        System.out.println("setDatosAccesorioFacade");
        DatosAccesorioFacade datosAccesorioFacade = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setDatosAccesorioFacade(datosAccesorioFacade);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDatosAccesorio method, of class DatosAccesorioController.
     */
    @Test
    public void testGetDatosAccesorio() {
        System.out.println("getDatosAccesorio");
        DatosAccesorioController instance = new DatosAccesorioController();
        DatosAccesorio expResult = null;
        DatosAccesorio result = instance.getDatosAccesorio();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDatosAccesorio method, of class DatosAccesorioController.
     */
    @Test
    public void testSetDatosAccesorio() {
        System.out.println("setDatosAccesorio");
        DatosAccesorio datosAccesorio = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setDatosAccesorio(datosAccesorio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumnas method, of class DatosAccesorioController.
     */
    @Test
    public void testGetColumnas() {
        System.out.println("getColumnas");
        DatosAccesorioController instance = new DatosAccesorioController();
        Columnas expResult = null;
        Columnas result = instance.getColumnas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColumnas method, of class DatosAccesorioController.
     */
    @Test
    public void testSetColumnas() {
        System.out.println("setColumnas");
        Columnas columnas = null;
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setColumnas(columnas);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSkuName method, of class DatosAccesorioController.
     */
    @Test
    public void testGetSkuName() {
        System.out.println("getSkuName");
        DatosAccesorioController instance = new DatosAccesorioController();
        String expResult = "";
        String result = instance.getSkuName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSkuName method, of class DatosAccesorioController.
     */
    @Test
    public void testSetSkuName() {
        System.out.println("setSkuName");
        String skuName = "";
        DatosAccesorioController instance = new DatosAccesorioController();
        instance.setSkuName(skuName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
