package com.webshop.dtos;

public class OceniProdavcaDto {
    private int ocena;
    private String komentar;
    private int proizvodId;


    public OceniProdavcaDto() {}

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getProizvodId() {
        return proizvodId;
    }

    public void setProizvodId(int proizvodId) {
        this.proizvodId = proizvodId;
    }



    public OceniProdavcaDto(int ocena, String komentar, int proizvodId) {
        this.ocena = ocena;
        this.komentar = komentar;
        this.proizvodId = proizvodId;

    }
}
