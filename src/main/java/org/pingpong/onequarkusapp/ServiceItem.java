package org.pingpong.onequarkusapp;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.pingpong.onequarkusapp.dominio.NormalItemAR;
import org.pingpong.onequarkusapp.dominio.OrdenAR;
import org.pingpong.onequarkusapp.dominio.UsuariaAR;

@ApplicationScoped
public class ServiceItem {

    public ServiceItem() {}
    
    public UsuariaAR cargaUsuaria(String name) {
        Optional<UsuariaAR> usuaria = UsuariaAR.findByIdOptional(name);
        return usuaria.isPresent()? usuaria.get(): new UsuariaAR();
    }

    public NormalItemAR cargaItem(String name) {
        Optional<NormalItemAR> item = NormalItemAR.findByIdOptional(name);
        return item.isPresent()? item.get(): new NormalItemAR();
    }

    public List<OrdenAR> cargaOrden(String usuaria_name) {
        return OrdenAR.findByUserName(usuaria_name);
    }

    // contenido min eval: if-else
    @Transactional
    public OrdenAR comanda(String usuaria_name, String item_name) {
        OrdenAR orden = null;
        Optional<UsuariaAR> usuaria = UsuariaAR.findByIdOptional(usuaria_name);
        Optional<NormalItemAR> item = NormalItemAR.findByIdOptional(item_name);
        if (usuaria.isPresent() & item.isPresent()) {
            orden = new OrdenAR(usuaria.get(), item.get());
            orden.persist();
        }
        return orden;
    }
}
