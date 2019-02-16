package br.com.lanza.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.lanza.cursomc.domain.Categoria;
import br.com.lanza.cursomc.domain.Cidade;
import br.com.lanza.cursomc.domain.Cliente;
import br.com.lanza.cursomc.domain.Endereco;
import br.com.lanza.cursomc.domain.Estado;
import br.com.lanza.cursomc.domain.ItemPedido;
import br.com.lanza.cursomc.domain.Pagamento;
import br.com.lanza.cursomc.domain.PagamentoComBoleto;
import br.com.lanza.cursomc.domain.PagamentoComCartao;
import br.com.lanza.cursomc.domain.Pedido;
import br.com.lanza.cursomc.domain.Produto;
import br.com.lanza.cursomc.domain.enums.EstadoPagamento;
import br.com.lanza.cursomc.domain.enums.TipoCliente;
import br.com.lanza.cursomc.repositories.CategoriaRepository;
import br.com.lanza.cursomc.repositories.CidadeRepository;
import br.com.lanza.cursomc.repositories.ClienteRepository;
import br.com.lanza.cursomc.repositories.EnderecoRepository;
import br.com.lanza.cursomc.repositories.EstadoRepository;
import br.com.lanza.cursomc.repositories.ItemPedidoRepository;
import br.com.lanza.cursomc.repositories.PagamentoRepository;
import br.com.lanza.cursomc.repositories.PedidoRepository;
import br.com.lanza.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		Cidade c4 = new Cidade(null, "Araraquara", est2);
		Cidade c5 = new Cidade(null, "Belo Horizonte", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c5));
		est2.getCidades().addAll(Arrays.asList(c2, c3, c4));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12312312312", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12341234", "123451234"));
		Cliente cli2 = new Cliente(null, "Guilherme Lanza", "lanza@gmail.com", "00000000191", TipoCliente.PESSOAFISICA);
		cli2.getTelefones().addAll(Arrays.asList("12341234", "123451234"));

		Endereco e1 = new Endereco(null, "Rua Teste", "777", "Complemento", "Jd Teste", "11411-000", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Testes", "7747", "Complemento2", "Jd Teste2", "14411-000", cli1, c2);
		Endereco e3 = new Endereco(null, "Rua 9 de Julho", "111", "Complemento", "Centro", "14800-000", cli2, c4);

		cli1.getEndereco().addAll(Arrays.asList(e1, e2));
		cli2.getEndereco().addAll(Arrays.asList(e3));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("10/02/2019 10:23"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("01/02/2019 13:15"), cli2, e3);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("02/02/2019 09:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
