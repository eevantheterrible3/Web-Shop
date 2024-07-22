package com.webshop.dtos;

public class OceniKupcaDto {
    private int ocena;
    private String komentar;
    private int proizvodId;


    public OceniKupcaDto() {}

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



    public OceniKupcaDto(int ocena, String komentar, int proizvodId) {
        this.ocena = ocena;
        this.komentar = komentar;
        this.proizvodId = proizvodId;

    }
}
