package com.duoc.bank_houston.entidades.cuentas;

import com.duoc.bank_houston.utilitarios.Utilitarios;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class CuentaInversion extends CuentaBancaria{
    private double interesInversion;

    public CuentaInversion(int numero, int saldo, double interesInversion){
        super(numero, saldo);
        this.interesInversion = interesInversion;
    }

    public double getInteresInversion() {
        return interesInversion;
    }
    
    @Override
    int calcularInteres(int monto, double tasa) {
        return (int)(Math.round(monto * (1 + tasa)));
    }
    
    public int pedirGiro(int saldoActual){
        Scanner teclado = new Scanner(System.in);
        int giro = 0;
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
        formato.setGroupingUsed(true);
                
        try{
            do{
                System.out.println("INGRESE EL MONTO A GIRAR");
                System.out.println("");
                giro = teclado.nextInt();
                if(giro <= 0){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A GIRAR DEBE SER MAYOR A CERO (0) PESOS");
                    System.out.println("");
                }
                if(giro > saldoActual){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A GIRAR NO PUEDE SER MAYOR AL SALDO ACTUAL ($"+formato.format(saldoActual)+")");
                    System.out.println("");
                }
            }while(giro <= 0 || giro > saldoActual);
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: EL MONTO INGRESADO NO ES UN NUMERO");
            System.out.println("");
            pedirGiro(saldoActual);
        }
        return giro;
    }
}