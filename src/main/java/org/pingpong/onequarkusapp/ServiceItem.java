package org.pingpong.onequarkusapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.pingpong.onequarkusapp.dominio.Item;
import org.pingpong.onequarkusapp.dominio.Orden;
import org.pingpong.onequarkusapp.dominio.Usuaria;

@ApplicationScoped
public class ServiceItem {

    public ServiceItem() {}
    
    public Usuaria cargaUsuaria(String name) {
        Optional<Usuaria> usuaria = Usuaria.findByIdOptional(name);
        return usuaria.isPresent()? usuaria.get(): new Usuaria();
    }

    public Item cargaItem(String name) {
        Optional<Item> item = Item.findByIdOptional(name);
        return item.isPresent()? item.get(): new Item();
    }

    public List<Orden> cargaOrden(String usuaria_name) {
        return Orden.findByUserName(usuaria_name);
    }

    // contenido min eval: if-else
    @Transactional
    public Orden comanda(String usuaria_name, String item_name) {
        Orden orden = null;
        Optional<Usuaria> usuaria = Usuaria.findByIdOptional(usuaria_name);
        Optional<Item> item = Item.findByIdOptional(item_name);
        if (usuaria.isPresent() && item.isPresent() 
            && usuaria.get().getDestreza() >= item.get().getQuality()) {
            orden = new Orden(usuaria.get(), item.get());
            orden.persist();
        }
        return orden;
    }

    // contenido min eval: loop, if-else, colecciones
    @Transactional
    public List<Orden> comandaMultiple(String usuaria, List<String> productos) {

        Optional<Usuaria> user = Usuaria.findByIdOptional(usuaria);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Orden> ordenes = new ArrayList<Orden>();

        Orden orden = null;
        for (String producto: productos) {
            orden = this.comanda(user.get().getNombre(), producto);
            if (orden != null) {
                ordenes.add(orden);
            }
        }
        return ordenes;      
    }
}
