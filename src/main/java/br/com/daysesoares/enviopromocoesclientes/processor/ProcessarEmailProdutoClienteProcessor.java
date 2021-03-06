package br.com.daysesoares.enviopromocoesclientes.processor;

import java.text.NumberFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import br.com.daysesoares.enviopromocoesclientes.dominio.InteresseProdutoCliente;

@Component
public class ProcessarEmailProdutoClienteProcessor implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage>{
	
	@Override
	public SimpleMailMessage process(InteresseProdutoCliente interesseProdutoCliente) throws InterruptedException {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("xpto.reply.com");
		email.setTo(interesseProdutoCliente.getCliente().getEmail());
		email.setSubject("Promoção imperdível!!!!");
		email.setText(geraTextoPromocao(interesseProdutoCliente));
		Thread.sleep(3000);
		return email;
	}

	private String geraTextoPromocao(InteresseProdutoCliente interesseProdutoCliente) {
		StringBuilder writer = new StringBuilder();
		writer.append(String.format("Olá, %s! \n\n", interesseProdutoCliente.getCliente().getNome()));
		writer.append("Essa promoção pode ser do seu interesse: \n\n");
		writer.append(String.format("%s - %s \n\n", interesseProdutoCliente.getProduto().getNome(), 
													interesseProdutoCliente.getProduto().getDescricao()));
		writer.append(String.format("Por apenas: %s!", 
				NumberFormat.getCurrencyInstance().format(interesseProdutoCliente.getProduto().getPreco())));
		
		return writer.toString();
	}

}
