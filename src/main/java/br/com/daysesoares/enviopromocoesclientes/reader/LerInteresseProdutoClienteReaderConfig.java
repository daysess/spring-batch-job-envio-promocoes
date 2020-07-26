package br.com.daysesoares.enviopromocoesclientes.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import br.com.daysesoares.enviopromocoesclientes.dominio.Cliente;
import br.com.daysesoares.enviopromocoesclientes.dominio.InteresseProdutoCliente;
import br.com.daysesoares.enviopromocoesclientes.dominio.Produto;

@Configuration
public class LerInteresseProdutoClienteReaderConfig {

	@Bean
	public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(
			@Qualifier("appDataSource") DataSource dataSource){
		return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
				.name("lerInteresseProdutoClienteReader")
				.dataSource(dataSource)
				.sql("select *, c.nome as nm_cliente, p.nome as nm_produto "
						+ "from interesse_produto_cliente ipc "
						+ "join cliente c on (ipc.cliente = c.id) "
						+ "join produto p on (p.produto = p.id) ")
				.rowMapper(rowMapper())
				.build();
		
	}

	private RowMapper<InteresseProdutoCliente> rowMapper() {
		return new RowMapper<InteresseProdutoCliente>() {

			@Override
			public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cliente cliente = new Cliente(
						rs.getInt("cliente"), 
						rs.getString("nm_cliente"), 
						rs.getString("email"));
				
				Produto produto = new Produto(
						rs.getInt("produto"), 
						rs.getString("nm_produto"), 
						rs.getString("descricao"), 
						rs.getDouble("preco"));
				
				InteresseProdutoCliente interesseProdutoCliente = new InteresseProdutoCliente(cliente, produto);
				
				return interesseProdutoCliente;
			}
			
		};
	}
	
}
