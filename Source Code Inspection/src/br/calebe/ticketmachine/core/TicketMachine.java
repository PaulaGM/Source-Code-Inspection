package br.calebe.ticketmachine.core;

import br.calebe.ticketmachine.exception.PapelMoedaInvalidaException;
import br.calebe.ticketmachine.exception.SaldoInsuficienteException;
import java.util.Iterator;

/**
 *
 * @author Calebe de Paula Bianchini
 */
public class TicketMachine {

    protected int valor;
    protected int saldo;
    protected int[] papelMoeda = {2, 5, 10, 20, 50, 100};

    public TicketMachine(int valor) {
        this.valor = valor;
        this.saldo = 0;
    }

    public void inserir(int quantia) throws PapelMoedaInvalidaException {
        boolean achou = false;
        int nota=-1;
        System.out.println("Aguarde alguns instantes, enquanto o sistema está validando as notas");
        for (int i = 0; i < papelMoeda.length && !achou; i++) {
            if (papelMoeda[i] == quantia) {
                achou = true;
            }
            nota=i;
        }
        if (!achou) {
            while(!achou){
                devolver(nota);
                System.out.println("Retire a nota não aceita");
                if(nota==-1) { achou=true; }
                else { nota = -1; }
            }
            throw new PapelMoedaInvalidaException();
        }
        else{
        System.out.println("A nota de papel moeda $" + papelMoeda[nota] + " foi aceita");
        this.saldo += quantia;
        System.out.println("O saldo atual é: " + saldo);
        }
    }

    public int getSaldo() {
        return saldo;
    }

//    public Iterator<Integer> getTroco() {
    public Iterator<PapelMoeda> getTroco() {
        if(saldo>0){
            Troco troco = new Troco(saldo);
            Iterator<PapelMoeda> notas = troco.getIterator();
            return notas;
        }
        return null;
    }

    public String imprimir() throws SaldoInsuficienteException {
        if (saldo < valor) {
            System.out.println("O saldo é insuficiente!");
            throw new SaldoInsuficienteException();
        }
        String result = "*****************\n";
        result += "*** R$ " + saldo + ",00 ****\n";
        result += "*****************\n";
        return result;
    }
    void devolver(int indice){
        papelMoeda[indice]--;
    }
}
