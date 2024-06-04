package com.duoc.bank_houston.entidades.cuentas;

import com.duoc.bank_houston.utilitarios.Utilitarios;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class CuentaAhorro extends CuentaBancaria{
    private double interesUso;
    private double interesAhorro;
    
    public CuentaAhorro(int numero, int saldo, double interesUso, double interesAhorro){
        super(numero, saldo);
        this.interesUso = interesUso;
        this.interesAhorro = interesAhorro;
    }

    public double getInteresUso() {
        return interesUso;
    }

    public double getInteresAhorro() {
        return interesAhorro;
    }

    @Override
    int calcularInteres(int monto, double tasa) {
        return (int)(Math.round(monto * (1 + tasa)));
    }
    
    public int pedirGiro(int saldoActual, double interesUso){
        Scanner teclado = new Scanner(System.in);
        int giro = 0;
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
        formato.setGroupingUsed(true);
                
        try{
            do{
                System.out.println("INGRESE EL MONTO A GIRAR");
                System.out.println("CUENTA DE INVERSION. SE COBRARA UNA COMISION DEL "+(int)(interesUso * 100)+"%");
                System.out.println("");
                giro = teclado.nextInt();
                giro = calcularInteres(giro, interesUso);
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
            pedirGiro(saldoActual, interesUso);
        }
        return giro;
    }
}
