package model;

public class DestilariaUtilizaIngrediente {
    private Destilaria destilaria;
    private Ingrediente ingrediente;

    public DestilariaUtilizaIngrediente(Destilaria destilaria, Ingrediente ingrediente) {
        this.destilaria = destilaria;
        this.ingrediente = ingrediente;
    }

    @Override
    public String toString() {
        return "DestilariaUtilizaIngrediente{" + "destilaria=" + destilaria + ", ingrediente=" + ingrediente + '}';
    }

    public Destilaria getDestilaria() {
        return destilaria;
    }

    public void setDestilaria(Destilaria destilaria) {
        this.destilaria = destilaria;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

   
}
