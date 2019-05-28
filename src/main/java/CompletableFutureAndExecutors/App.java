package CompletableFutureAndExecutors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    static ExecutorService pool = Executors.newFixedThreadPool(4);
	static List<CompletableFuture<Tarefa>> chamadas = new ArrayList<>();

	public static void main(String[] args)  {
		
		List<String> itens = Arrays.asList( "Job01","Job02","Job03","Job04");
		List<Tarefa> tarefas = new ArrayList<>();
		itens.forEach(item->tarefas.add(new Tarefa(item)));
		
		tarefas.forEach(tarefa->addChamada(tarefa));

		try {
		    if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
		    	pool.shutdownNow();
		    } 
		} catch (InterruptedException e) {
			pool.shutdownNow();
		}
		
		
	}
	static Tarefa verificaChamada(Tarefa tarefa) {
		tarefa.fimSalvarArquivo();
		if(EstadoTarefa.ProcessadaComErro.equals(tarefa.getEstado())
				||
				EstadoTarefa.ErroProcessamento.equals(tarefa.getEstado())) {
			addChamada(tarefa); 
		}
		return tarefa;
	}
	
	static void addChamada(Tarefa tarefa) {
		chamadas.add(
				CompletableFuture.supplyAsync(()->tarefa.salvarArquivo(),pool)
				//.thenApply(t ->t.fimSalvarArquivo())
				//.thenApply(Tarefa::fimSalvarArquivo)
				.thenApply(t ->verificaChamada(t))
			);
		
	}

}
