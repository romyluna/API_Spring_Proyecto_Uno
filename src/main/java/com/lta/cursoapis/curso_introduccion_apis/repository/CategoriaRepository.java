    package com.lta.cursoapis.curso_introduccion_apis.repository;


    import com.lta.cursoapis.curso_introduccion_apis.entity.Categoria;
    import org.springframework.data.domain.Example;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

        Optional<Categoria> findByNombreCategoria(String nombreCategoria);

        boolean existsByNombreCategoria(String nombreCategoria);
    }
