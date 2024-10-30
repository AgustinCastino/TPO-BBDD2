package com.uade.BBDD2.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity getHotele(@PathVariable int idHotel) {
        return ResponseEntity.ok("Hotel");
    }
}
