package CompletableFutureAndExecutors;

import java.util.Random;

public class Tarefa {
	private String arquivo;
	private EstadoTarefa estado = EstadoTarefa.NaoProcessada;
	
	
	public Tarefa(String arquivo) {
		super();
		this.arquivo = arquivo;
	}

	public String getArquivo() {
		return arquivo;
	}
	
	
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	public void setEstado(EstadoTarefa estado) {
		this.estado = estado;
	}
	public EstadoTarefa getEstado() {
		return estado;
	}
	
	public Tarefa salvarArquivo()  {
		try {
			System.out.println("Processando "+getArquivo());
			Thread.sleep(500);
		} catch (InterruptedException e) {
			setEstado(EstadoTarefa.ErroProcessamento);
		}
		int num = new Random().nextInt(2);
		//System.out.println("Num random: "+num);
		if (num % 2 == 0) {	 
			setEstado(EstadoTarefa.ProcessadaComSucesso);
		}else {
			setEstado(EstadoTarefa.ProcessadaComErro);
		}
		return this;
	}
	
	public Tarefa fimSalvarArquivo()  {
		System.out.println(getArquivo() + getEstado().toString());
		return this;
	}
}
