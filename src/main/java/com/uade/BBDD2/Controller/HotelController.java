package com.uade.BBDD2.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hotel")
public class HotelController {
    @GetMapping("")
    public ResponseEntity getHoteles() throws Exception{
        return ResponseEntity.ok("Devolver Hoteles");
    }

    @PostMapping("/crear")
    public ResponseEntity crearHotel(){
        return ResponseEntity.ok("Creado");
    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminarHotel(){
        return ResponseEntity.ok("Eliminado");
    }
}
