package com.duoc.bank_houston.app;

import com.duoc.bank_houston.entidades.clientes.Cliente;
import com.duoc.bank_houston.entidades.cuentas.CuentaAhorro;
import com.duoc.bank_houston.entidades.cuentas.CuentaCorriente;
import com.duoc.bank_houston.entidades.cuentas.CuentaInversion;
import com.duoc.bank_houston.utilitarios.Utilitarios;
import static com.duoc.bank_houston.utilitarios.Utilitarios.limpiaPantalla;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class App {
    static ArrayList<String> itemsMenu = new ArrayList<>();
    static ArrayList<Integer> cuentasUsadas = new ArrayList<>();
    static HashMap<Integer, CuentaAhorro> cuentasAhorros = new HashMap<>();
    static HashMap<Integer, CuentaCorriente> cuentasCorrientes = new HashMap<>();
    static HashMap<Integer, CuentaInversion> cuentasInversion = new HashMap<>();
    static HashMap<String, Cliente> clientesRegistrados = new HashMap<>();
    static String[] arrayOpciones = {"CUENTA DE AHORRO", "CUENTA CORRIENTE", "CUENTA DE INVERSION"};
    
    public static void main(String[] args) {
        itemsMenu.add("REGISTRAR CLIENTE");
        itemsMenu.add("VER DATOS DEL CLIENTE");
        itemsMenu.add("DEPOSITAR");
        itemsMenu.add("GIRAR");
        itemsMenu.add("CONSULTA SALDO");
        itemsMenu.add("VER TIPOS DE CUENTAS");
        itemsMenu.add("SALIR");
        
        Utilitarios.limpiaPantalla();
        menuPrincipal();
    }
    
    static void menuPrincipal(){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        try{
            do{
                System.out.println("BIENVENIDO A BANK BOSTON");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenu.size(); i++){
                    System.out.println("["+(i + 1)+"] "+itemsMenu.get(i));
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.size()){
                    Utilitarios.limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.size());
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuPrincipal();
        }
        
        if(opcion == 1){
            Utilitarios.limpiaPantalla();
            registrarCliente();
        }
        
        if(opcion == 2){
            Utilitarios.limpiaPantalla();
            verDatosCliente();
        }
        
        if(opcion == 3){
            Utilitarios.limpiaPantalla();
            depositoCliente();
        }
        
        if(opcion == 4){
            Utilitarios.limpiaPantalla();
            giroCliente();
        }
        
        if(opcion == 5){
           Utilitarios.limpiaPantalla();
           verSaldoCliente();
        }
        
        if(opcion == 6){
            Utilitarios.limpiaPantalla();
            verTiposCuentas();
        }
        
        if(opcion == 7){
            Utilitarios.limpiaPantalla();
            System.out.println("GRACIAS POR USAR BANK HOUSTON");
        }
    }
    
    static void registrarCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        String NOM;
        String AP;
        String AM;
        String DOM;
        String COM;
        String TEL;
        int TIPO;
        int CTA;
        boolean rutEsValido = true;
        
        do{
            Utilitarios.limpiaPantalla();
            if(rutEsValido == false) System.out.println("** EL RUT INGRESADO NO ES VALIDO **");
            System.out.println("INGRESE RUT DEL CLIENTE:");
            System.out.println("* ingrese RUT con puntos y guion. EJ: 11.222.333-4");
            RUT = teclado.nextLine();
            rutEsValido = Utilitarios.validaRut(RUT);
        }while(rutEsValido == false);
        RUT = RUT.toUpperCase();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE NOMBRE DEL CLIENTE:");
        NOM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE APELLIDO PATERNO DEL CLIENTE:");
        AP = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE APELLIDO MATERNO DEL CLIENTE:");
        AM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE DOMICILIO DEL CLIENTE:");
        DOM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE COMUNA DEL DOMICILIO DEL CLIENTE:");
        COM = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        System.out.println("INGRESE TELEFONO DEL CLIENTE:");
        TEL = teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        TIPO = pedirTipoCuenta();
        
        do{
            CTA = Utilitarios.generarNroCtaCte();
        }while(cuentasUsadas.contains(CTA));
        
        cuentasUsadas.add(CTA);
        
        if(TIPO == 1){
            CuentaAhorro auxCta = new CuentaAhorro(CTA, 0, 0.03, 0.02);
            cuentasAhorros.put(CTA, auxCta);
        }
        if(TIPO == 2){
            CuentaCorriente auxCta = new CuentaCorriente(CTA, 0);
            cuentasCorrientes.put(CTA, auxCta);
        }
        if(TIPO == 3){
            CuentaInversion auxCta = new CuentaInversion(CTA, 0, 0.05);
            cuentasInversion.put(CTA, auxCta);
        }

        Cliente auxCte = new Cliente(RUT, NOM, AP, AM, DOM, COM, TEL, TIPO, CTA);
        clientesRegistrados.put(RUT, auxCte);
        
        Utilitarios.limpiaPantalla();
        System.out.println("CLIENTE REGISTRADO EXITOSAMENTE!");
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        menuPrincipal();
    }

    static void verDatosCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        int saldoCuenta = 0;
        CuentaAhorro auxCuentaAhorro = null;
        CuentaCorriente auxCuentaCorriente = null;
        CuentaInversion auxCuentaCredito = null;
        
        if(!Utilitarios.hayClientesRegistrados(clientesRegistrados, "NO HAY CLIENTES REGISTRADOS PARA MOSTRAR DATOS")){
            menuPrincipal();
        }else{
            RUT = Utilitarios.validaClienteRegistrado();
            
            if(!Utilitarios.clienteEsUsuario(clientesRegistrados, RUT)){
                menuPrincipal();
            }else{
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int tipoCuenta = auxCliente.getTipoCuenta();
                int nroCuenta = auxCliente.getNumeroCuenta();
                
                if(tipoCuenta == 1){
                    auxCuentaAhorro = cuentasAhorros.get(nroCuenta);
                    saldoCuenta = auxCuentaAhorro.getSaldo();
                }
                if(tipoCuenta == 2){
                    auxCuentaCorriente = cuentasCorrientes.get(nroCuenta);
                    saldoCuenta = auxCuentaCorriente.getSaldo();
                }
                if(tipoCuenta == 3){
                    auxCuentaCredito = cuentasInversion.get(nroCuenta);
                    saldoCuenta = auxCuentaCredito.getSaldo();
                }
                
                auxCliente.mostrarInformacionCliente(auxCliente, saldoCuenta);
                
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }   
    }

    static void verSaldoCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        int saldoCuenta = 0;
        CuentaAhorro auxCuentaAhorro = null;
        CuentaCorriente auxCuentaCorriente = null;
        CuentaInversion auxCuentaCredito = null;
        
        if(!Utilitarios.hayClientesRegistrados(clientesRegistrados, "NO HAY CLIENTES REGISTRADOS PARA MOSTRAR DATOS")){
            menuPrincipal();
        }else{
            RUT = Utilitarios.validaClienteRegistrado();
            
            if(!Utilitarios.clienteEsUsuario(clientesRegistrados, RUT)){
                menuPrincipal();
            }else{
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                int tipoCuenta = auxCliente.getTipoCuenta();
                
                if(tipoCuenta == 1){
                    auxCuentaAhorro = cuentasAhorros.get(nroCuenta);
                    saldoCuenta = auxCuentaAhorro.getSaldo();
                }
                if(tipoCuenta == 2){
                    auxCuentaCorriente = cuentasCorrientes.get(nroCuenta);
                    saldoCuenta = auxCuentaCorriente.getSaldo();
                }
                if(tipoCuenta == 3){
                    auxCuentaCredito = cuentasInversion.get(nroCuenta);
                    saldoCuenta = auxCuentaCredito.getSaldo();
                }
                
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** SALDO ACTUAL DEL CLIENTE **");
                System.out.println("");
                System.out.println("SALDO CUENTA:     $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }
    }

    static void depositoCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        int deposito = 0;
        int saldoCuenta = 0;
        CuentaAhorro auxCuentaAhorro = null;
        CuentaCorriente auxCuentaCorriente = null;
        CuentaInversion auxCuentaCredito = null;
        
        if(!Utilitarios.hayClientesRegistrados(clientesRegistrados, "NO HAY CLIENTES REGISTRADOS PARA MOSTRAR DATOS")){
            menuPrincipal();
        }else{
            RUT = Utilitarios.validaClienteRegistrado();
            
            if(!Utilitarios.clienteEsUsuario(clientesRegistrados, RUT)){
                menuPrincipal();
            }else{
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** REALIZAR DEPOSITO **");
                System.out.println("");
                
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                int tipoCuenta = auxCliente.getTipoCuenta();
                
                if(tipoCuenta == 1){
                    auxCuentaAhorro = cuentasAhorros.get(nroCuenta);
                    saldoCuenta = auxCuentaAhorro.getSaldo();
                    deposito = auxCuentaAhorro.pedirDeposito(auxCuentaAhorro.getInteresAhorro());
                }
                if(tipoCuenta == 2){
                    auxCuentaCorriente = cuentasCorrientes.get(nroCuenta);
                    saldoCuenta = auxCuentaCorriente.getSaldo();
                    deposito = auxCuentaCorriente.pedirDeposito();
                }
                if(tipoCuenta == 3){
                    auxCuentaCredito = cuentasInversion.get(nroCuenta);
                    saldoCuenta = auxCuentaCredito.getSaldo();
                    deposito = auxCuentaCredito.pedirDeposito(auxCuentaCredito.getInteresInversion());
                }

                saldoCuenta += deposito;
                
                if(tipoCuenta == 1){
                    auxCuentaAhorro = cuentasAhorros.get(nroCuenta);
                    auxCuentaAhorro.setSaldo(saldoCuenta);
                    cuentasAhorros.put(nroCuenta, auxCuentaAhorro);
                }
                if(tipoCuenta == 2){
                    auxCuentaCorriente = cuentasCorrientes.get(nroCuenta);
                    auxCuentaCorriente.setSaldo(saldoCuenta);
                    cuentasCorrientes.put(nroCuenta, auxCuentaCorriente);
                }
                if(tipoCuenta == 3){
                    auxCuentaCredito = cuentasInversion.get(nroCuenta);
                    auxCuentaCredito.setSaldo(saldoCuenta);
                    cuentasInversion.put(nroCuenta, auxCuentaCredito);
                }
                
                Utilitarios.limpiaPantalla();
                System.out.println("DEPOSITO REALIZADO DE MANERA EXITOSA!");
                System.out.println("USTED TIENE UN SALDO ACTUAL DE: $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }
    }
    
    static void giroCliente(){
        Scanner teclado = new Scanner(System.in);
        String RUT;
        int giro = 0;
        int saldoCuenta = 0;
        CuentaAhorro auxCuentaAhorro = null;
        CuentaCorriente auxCuentaCorriente = null;
        CuentaInversion auxCuentaCredito = null;
        
        if(!Utilitarios.hayClientesRegistrados(clientesRegistrados, "NO HAY CLIENTES REGISTRADOS PARA MOSTRAR DATOS")){
            menuPrincipal();
        }else{
            RUT = Utilitarios.validaClienteRegistrado();
            
            if(!Utilitarios.clienteEsUsuario(clientesRegistrados, RUT)){
                menuPrincipal();
            }else{
                NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
                formato.setGroupingUsed(true);
                
                Utilitarios.limpiaPantalla();
                System.out.println("** REALIZAR GIRO **");
                System.out.println("");
                
                Cliente auxCliente = clientesRegistrados.get(RUT);
                int nroCuenta = auxCliente.getNumeroCuenta();
                int tipoCuenta = auxCliente.getTipoCuenta();
                
                if(tipoCuenta == 1){
                    auxCuentaAhorro = cuentasAhorros.get(nroCuenta);
                    saldoCuenta = auxCuentaAhorro.getSaldo();
                    giro = auxCuentaAhorro.pedirGiro(saldoCuenta, auxCuentaAhorro.getInteresUso());
                }
                if(tipoCuenta == 2){
                    auxCuentaCorriente = cuentasCorrientes.get(nroCuenta);
                    saldoCuenta = auxCuentaCorriente.getSaldo();
                    giro = auxCuentaCorriente.pedirGiro(saldoCuenta);
                }
                if(tipoCuenta == 3){
                    auxCuentaCredito = cuentasInversion.get(nroCuenta);
                    saldoCuenta = auxCuentaCredito.getSaldo();
                    giro = auxCuentaCredito.pedirGiro(saldoCuenta);
                }
                
                saldoCuenta -= giro;
                
                if(tipoCuenta == 1){
                    auxCuentaAhorro = cuentasAhorros.get(nroCuenta);
                    auxCuentaAhorro.setSaldo(saldoCuenta);
                    cuentasAhorros.put(nroCuenta, auxCuentaAhorro);
                }
                if(tipoCuenta == 2){
                    auxCuentaCorriente = cuentasCorrientes.get(nroCuenta);
                    auxCuentaCorriente.setSaldo(saldoCuenta);
                    cuentasCorrientes.put(nroCuenta, auxCuentaCorriente);
                }
                if(tipoCuenta == 3){
                    auxCuentaCredito = cuentasInversion.get(nroCuenta);
                    auxCuentaCredito.setSaldo(saldoCuenta);
                    cuentasInversion.put(nroCuenta, auxCuentaCredito);
                }
                
                Utilitarios.limpiaPantalla();
                System.out.println("GIRO REALIZADO DE MANERA EXITOSA!");
                System.out.println("USTED TIENE UN SALDO ACTUAL DE: $"+formato.format(saldoCuenta));
                System.out.println("");
                System.out.println("Presione ENTER para continuar...");
                teclado.nextLine();

                Utilitarios.limpiaPantalla();
                menuPrincipal();
            }
        }
    }
    
    static int pedirTipoCuenta(){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        try{
            do{
                System.out.println("SELECCIONE EL TIPO DE CUENTA A ABRIR");
                System.out.println("");
                for (int i=0; i<arrayOpciones.length; i++){
                    System.out.println("["+(i + 1)+"] "+arrayOpciones[i]);
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > arrayOpciones.length){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** LA OPCION INGRESADA NO ES VALIDA");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > arrayOpciones.length);
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            pedirTipoCuenta();
        }
        return opcion;
    }
    
    public static void verTiposCuentas(){
        Scanner teclado = new Scanner(System.in);
        limpiaPantalla();
        System.out.println("------------ CUENTA DE AHORRO ----------------");
        System.out.println("| CUENTA EN PESOS                            |");
        System.out.println("| CADA DEPOSITO GENERA UNA GANANCIA DEL 2%   |");
        System.out.println("| CADA GIRO GENERA UNA COMISION DEL 3%       |");
        System.out.println("----------------------------------------------");
        System.out.println("");
        System.out.println("------------ CUENTA CORRIENTE ----------------");
        System.out.println("| CUENTA EN PESOS                            |");
        System.out.println("| GIROS ILIMITADOS SIN COMISION              |");
        System.out.println("----------------------------------------------");
        System.out.println("");
        System.out.println("------------ CUENTA DE INVERSION -------------");
        System.out.println("| CUENTA EN PESOS                            |");
        System.out.println("| CADA DEPOSITO GENERA UNA GANANCIA DEL 5%   |");
        System.out.println("| GIROS SIN COMISION                         |");
        System.out.println("----------------------------------------------");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        
        Utilitarios.limpiaPantalla();
        menuPrincipal();
    }  
}
