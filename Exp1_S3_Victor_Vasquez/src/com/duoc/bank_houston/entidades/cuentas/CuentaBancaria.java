package com.duoc.bank_houston.entidades.cuentas;

import com.duoc.bank_houston.utilitarios.Utilitarios;
import java.util.Scanner;

public abstract class CuentaBancaria {
    private int numero;
    private int saldo;
    
    public CuentaBancaria(int numero, int saldo){
        this.numero = numero;
        this.saldo = saldo;
    }
    
    abstract int calcularInteres(int monto, double tasa);
    
    public int pedirDeposito(){
        Scanner teclado = new Scanner(System.in);
        int deposito = 0;
        try{
            do{
                System.out.println("INGRESE EL MONTO A DEPOSITAR");
                System.out.println("");
                deposito = teclado.nextInt();
                if(deposito <= 0){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A DEPOSITAR DEBE SER MAYOR A CERO (0) PESOS");
                    System.out.println("");
                }
            }while(deposito <= 0);
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: EL MONTO INGRESADO NO ES UN NUMERO");
            System.out.println("");
            pedirDeposito();
        }
        return deposito;
    }
    
    public int pedirDeposito(double interesInversion){
        Scanner teclado = new Scanner(System.in);
        int deposito = 0;
        try{
            do{
                System.out.println("INGRESE EL MONTO A DEPOSITAR");
                System.out.println("SU DEPOSITO GENERARA UNA GANACIA DEL "+(int)(interesInversion * 100)+"%");
                System.out.println("");
                deposito = teclado.nextInt();
                if(deposito <= 0){
                    Utilitarios.limpiaPantalla();
                    System.out.println("** EL MONTO A DEPOSITAR DEBE SER MAYOR A CERO (0) PESOS");
                    System.out.println("");
                }
            }while(deposito <= 0);
        }catch(Exception e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: EL MONTO INGRESADO NO ES UN NUMERO");
            System.out.println("");
            pedirDeposito();
        }
        return (int)(Math.round(deposito * (1 + interesInversion)));
    }

    public int getNumero() {
        return numero;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
