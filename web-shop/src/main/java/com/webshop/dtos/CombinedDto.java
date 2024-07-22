package com.webshop.dtos;

public class CombinedDto {

        private OceniProdavcaDto oceniProdavcaDto;
        private KorisnikDto logovani;

        // Getters and setters
        public OceniProdavcaDto getOceniProdavcaDto() {
            return oceniProdavcaDto;
        }

        public void setOceniProdavcaDto(OceniProdavcaDto oceniProdavcaDto) {
            this.oceniProdavcaDto = oceniProdavcaDto;
        }

        public KorisnikDto getLogovani() {
            return logovani;
        }

        public void setLogovani(KorisnikDto logovani) {
            this.logovani = logovani;
        }
    }

