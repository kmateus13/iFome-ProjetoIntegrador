package com.unifacisa.iFome.produto;

import java.math.BigDecimal;

import com.unifacisa.iFome.restaurante.Restaurante;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private String descricao;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	private String imagemUrl;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;
}
