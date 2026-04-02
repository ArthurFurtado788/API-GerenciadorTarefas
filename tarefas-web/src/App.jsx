// Componente principal da aplicação React para gerenciamento de tarefas
import { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [tarefas, setTarefas] = useState([]);
  const [novoTitulo, setNovoTitulo] = useState('');
  const [novaDescricao, setNovaDescricao] = useState('');
  const [editingId, setEditingId] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const pendencias = tarefas.filter((t) => !t.concluida).length;

  const API_URL = "http://localhost:8080/tarefas";

  useEffect(() => {
    buscarTarefas();
  }, []);

  const buscarTarefas = async () => {
    setIsLoading(true);
    try {
      const res = await axios.get(API_URL);
      setTarefas(res.data);
    } catch (err) {
      console.error("Erro ao buscar tarefas", err);
      alert("Não foi possível buscar tarefas. Verifique se o backend está rodando.");
    } finally {
      setIsLoading(false);
    }
  };

  const salvarTarefa = async (e) => {
    e.preventDefault();

    const payload = {
      titulo: novoTitulo,
      descricao: novaDescricao,
      concluida: false,
    };

    try {
      if (editingId) {
        const tarefa = tarefas.find((t) => t.id === editingId);
        if (tarefa) {
          await axios.put(`${API_URL}/${editingId}`, {
            ...tarefa,
            ...payload,
            concluida: tarefa.concluida,
          });
        }
      } else {
        await axios.post(API_URL, payload);
      }
      setNovoTitulo('');
      setNovaDescricao('');
      setEditingId(null);
      buscarTarefas();
    } catch (err) {
      console.error("Erro ao salvar tarefa", err);
      alert("Erro ao salvar tarefa. Veja se o backend oferece PUT/POST no endpoint.");
    }
  };

  const deletarTarefa = async (id) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      buscarTarefas();
    } catch (err) {
      console.error("Erro ao deletar tarefa", err);
      alert("Erro ao deletar tarefa");
    }
  };

  const editarTarefa = (tarefa) => {
    setEditingId(tarefa.id);
    setNovoTitulo(tarefa.titulo || '');
    setNovaDescricao(tarefa.descricao || '');
  };

  const cancelarEdicao = () => {
    setEditingId(null);
    setNovoTitulo('');
    setNovaDescricao('');
  };

  const toggleConclusao = async (tarefa) => {
    try {
      await axios.put(`${API_URL}/${tarefa.id}`, {
        ...tarefa,
        concluida: !tarefa.concluida,
      });
      buscarTarefas();
    } catch (err) {
      console.error("Erro ao alternar conclusão", err);
      alert("Falha ao atualizar conclusão");
    }
  };

  const criarLembrete = (tarefa) => {
    alert(`Lembrete criado para '${tarefa.titulo || 'tarefa'}' (simulação).`);
  };

  const anexarArquivo = (tarefa) => {
    alert(`Anexar arquivo para '${tarefa.titulo || 'tarefa'}' (simulação).`);
  };


  return (
    <div className="flex h-screen bg-slate-50 font-sans text-slate-900">
      
      {/* HUB ESQUERDA: Painel de Cadastro (Fixo) */}
      <aside className="w-80 lg:w-96 bg-white border-r border-slate-200 p-8 shadow-2xl z-10 flex flex-col">
        <div className="mb-10">
          <h2 className="text-2xl font-black text-indigo-600 tracking-tight flex items-center gap-2">
            <span className="bg-indigo-100 p-2 rounded-lg text-xl">🎯</span> 
            TASK HUB
          </h2>
          <p className="text-slate-400 text-sm mt-2 font-medium">Gerenciador de Tarefas</p>
        </div>
        
        <form onSubmit={salvarTarefa} className="flex flex-col gap-5">
          <div className="space-y-1">
            <label className="text-xs font-bold uppercase tracking-widest text-slate-500 ml-1">O que fazer?</label>
            <input 
              className="w-full p-4 rounded-2xl border border-slate-200 bg-slate-50 focus:ring-2 focus:ring-indigo-500 focus:bg-white focus:border-transparent transition-all outline-none shadow-sm"
              placeholder="Ex: Finalizar API Java"
              value={novoTitulo}
              onChange={(e) => setNovoTitulo(e.target.value)}
              required
            />
          </div>

          <div className="space-y-1">
            <label className="text-xs font-bold uppercase tracking-widest text-slate-500 ml-1">Detalhes (Opcional)</label>
            <textarea 
              className="w-full p-4 h-40 rounded-2xl border border-slate-200 bg-slate-50 focus:ring-2 focus:ring-indigo-500 focus:bg-white transition-all outline-none resize-none shadow-sm"
              placeholder="Descreva os passos..."
              value={novaDescricao}
              onChange={(e) => setNovaDescricao(e.target.value)}
            />
          </div>

          <button className="mt-4 w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-4 rounded-2xl shadow-lg shadow-indigo-100 transition-all hover:-translate-y-1 active:scale-95 flex justify-center items-center gap-2">
            {editingId ? 'Salvar Alteração' : 'Criar Tarefa'}
          </button>
          {editingId && (
            <button type="button" onClick={cancelarEdicao} className="w-full mt-2 bg-slate-400 hover:bg-slate-500 text-white font-bold py-3 rounded-2xl transition-all">
              Cancelar Edição
            </button>
          )}
        </form>

        <div className="mt-auto pt-6 border-t border-slate-100 text-[10px] text-slate-400 text-center font-bold tracking-widest uppercase">
          ARTHUR PEREIRA FURTADO | DESENVOLVEDOR FULL STACK
        </div>
      </aside>

      {/* PAINEL DIREITA: Lista de Tarefas (Rolável) */}
      <main className="flex-1 overflow-y-auto p-12 custom-scrollbar">
        <div className="max-w-3xl mx-auto">
          <header className="flex justify-between items-end mb-12">
            <div>
              <h1 className="text-4xl font-black text-slate-800 tracking-tight">Suas Missões</h1>
              <p className="text-slate-500 font-medium">Você tem <span className="text-indigo-600 font-bold">{pendencias}</span> pendências registradas.</p>
            </div>
          </header>

          <div className="grid gap-6">
            {tarefas.length === 0 ? (
              <div className="text-center py-20 bg-white rounded-3xl border-2 border-dashed border-slate-200">
                <p className="text-slate-400 font-medium">Nenhuma tarefa por aqui... Descanse um pouco! ☕</p>
              </div>
            ) : (
              tarefas.map(t => (
                <div key={t.id} className="group bg-white border border-slate-200 p-6 rounded-3xl shadow-sm hover:shadow-xl hover:border-indigo-200 transition-all duration-300 relative overflow-hidden border-l-[12px] border-l-indigo-500">
                  
                  <div className="flex justify-between items-start gap-6">
                    <div className="flex-1 min-w-0">
                      <h3 className={`text-xl font-bold break-words leading-tight ${t.concluida ? 'line-through text-slate-300' : 'text-slate-800'}`}>
                        {t.titulo}
                      </h3>
                      <p className="mt-3 text-slate-500 text-sm break-words whitespace-pre-wrap leading-relaxed italic">
                        {t.descricao || "Nenhuma descrição detalhada."}
                      </p>
                    </div>

                    <div className="flex gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                      <button onClick={() => editarTarefa(t)} className="p-2 bg-slate-50 hover:bg-amber-50 text-amber-600 rounded-xl transition-colors" title="Editar">
                        ✏️
                      </button>
                      <button onClick={() => toggleConclusao(t)} className="p-2 bg-slate-50 hover:bg-emerald-50 text-emerald-600 rounded-xl transition-colors" title="Marcar como concluída">
                        {t.concluida ? '✔️' : '⏳'}
                      </button>
                      <button 
                        onClick={() => deletarTarefa(t.id)}
                        className="p-2 bg-slate-50 hover:bg-red-50 text-red-500 rounded-xl transition-colors" 
                        title="Excluir"
                      >
                        🗑️
                      </button>
                    </div>
                  </div>

                  <div className="mt-6 flex items-center gap-6 text-[10px] font-black uppercase tracking-tighter text-slate-400 border-t border-slate-50 pt-5">
                     <span onClick={() => anexarArquivo(t)} className="hover:text-indigo-600 cursor-pointer flex items-center gap-1">📎 Anexar Arquivo</span>
                     <span onClick={() => criarLembrete(t)} className="hover:text-indigo-600 cursor-pointer flex items-center gap-1">🔔 Criar Lembrete</span>
                     <span className="ml-auto px-3 py-1 bg-slate-100 rounded-full text-indigo-500">{t.concluida ? 'Finalizado' : 'Ativo'}</span>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </main>
    </div>
  );
}

export default App;