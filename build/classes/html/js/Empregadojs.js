// Get Input Elements
const idInputEmp       = document.getElementById('idInputEmp');
const nameInputEmp     = document.getElementById('nameInputEmp');
const cargoInputEmp =    document.getElementById('cargoInputEmp');
const salarioInputEmp =    document.getElementById('salarioInputEmp');
const idUpdateInputEmp       = document.getElementById('idUpdateInputEmp');
const nameUpdateInputEmp    = document.getElementById('nameUpdateInputEmp');
const cargoUpdateInputEmp     = document.getElementById('cargoUpdateInputEmp');
const salarioUpdateInputEmp     = document.getElementById('salarioUpdateInputEmp');


const inputConsultarId = document.getElementById('inputConsultarId');
const inputConsultarNome = document.getElementById('inputConsultarNome');
const inputConsultarCargo = document.getElementById('inputConsultarCargo');

// Get Content Elements
const loggerElement = document.getElementById('logger');
const EmpregadosElement = document.getElementById('empregados');
const empregadoSelectedElement = document.getElementById('empregadoSelected');

// Store fetched teachers
var empregados = [];
var empregadoSelected = null;

// =========================
// UTIL Methods
// =========================

var LogEnum = {
  INFO: "table-primary",
  WARNING: "table-warning",
  DANGER: "table-danger",
};

function addLog(msg, logEnum) {
  loggerElement.innerHTML = `<tr class="${logEnum}"><th scope="row">${new Date().toUTCString()}</th><td>${msg}</td>` + loggerElement.innerHTML;
}

function reloadEmpregados() {
  var listaApps = '';
  var n = empregados.length;
  for (var i = 0; i < n; i++) {
    listaApps += makeEmpregadoRow(empregados[i]);
  }
  EmpregadosElement.innerHTML = listaApps;
    
    
    inputConsultarId.value = '';
    inputConsultarNome.value = '';
    inputConsultarCargo.value = '';
    
}

function makeEmpregadoRow(empregado) {
  return `<tr><th scope="row">${empregado.id}</th>
          <td>${empregado.nome}</td><td>${empregado.cargo}</td><td>${empregado.salario}</td>
          <td class="text-center">
            <button type="button" class="btn btn-warning" onClick="prepareUpdateForm(${empregado.id})" data-toggle="modal" data-target="#updateModal">editar</button>
            <button type="button" class="btn btn-danger" onClick="prepareForDelete(${empregado.id})" data-toggle="modal" data-target="#deleteModal">apagar</button>
          </td>`;
}

function resetCreateForm() {
  idInputEmp.value = '';
  nameInputEmp.value = '';
  cargoInputEmp.value = '';
  salarioInputEmp.value = '';
}

function resetUpdateForm() {
  idUpdateInputEmp.value = '';
  nameUpdateInputEmp.value = '';
  cargoUpdateInputEmp.value = '';
  salarioUpdateInputEmp.value = '';
  empregadoSelected = null;
}

function resetDeleteModal() {
  deleteEmpregado.innerHTML = "";
  empregadoSelected = null;
}


// =========================
// CREATE Methods
// =========================
async function createEmpregado() {
  const URL = `/api/empregados`;
  const EmpregadoData = {
    'nome': nameInputEmp.value,
    'cargo': cargoInputEmp.value,
    'salario': salarioInputEmp.value  
  };
  const postRequest = {
    method: 'POST',
    body: JSON.stringify(EmpregadoData),
    headers: {
      'Content-type': 'application/json;charset=UTF-8'
    }
  };
  try {
    const resp = await fetch(URL, postRequest);
    if (resp.status == 200) {
      addLog(`Empregado ${EmpregadoData.nome} criado com sucesso`, LogEnum.INFO);
      await resetCreateForm();
      await readEmpregados();
    } else {
      addLog(`Criar Empregado: resposta diferente de 200 - ${resp.status}`, LogEnum.WARNING);
      await readEmpregados();
    }
  } catch (e) {
    addLog(`Exception during "createEmpregado()" - ${e}`, LogEnum.DANGER);
  }
}

// =========================
// READ Methods
// =========================
async function readEmpregados() {
  EmpregadosElement.innerHTML = 'carregando...';
  const url = `/api/empregados`;
  try {
    const resp = await fetch(url);
    empregados = await resp.json();
    addLog('Empregados carregados com sucesso',LogEnum.INFO);
    await reloadEmpregados();
  } catch (e) {
    addLog(`Exception during "readEmpregados()" - ${e}`, LogEnum.DANGER);
  }
}

async function readEmpregadoById()
{
EmpregadosElement.innerHTML = 'carregando...';
  const url = `/api/empregados/id/${inputConsultarId.value}`;
  try {
    const resp = await fetch(url);
    empregados = await resp.json();
    addLog(`Empregado de Id: ${inputConsultarId.value} carregado com sucesso`,LogEnum.INFO);
    await reloadEmpregados();
  } catch (e) {
    addLog(`Exception during "readEmpregados()" - ${e}`, LogEnum.DANGER);
  }
}

async function readEmpregadoByNome()
{
EmpregadosElement.innerHTML = 'carregando...';
  const url = `/api/empregados/nome/${inputConsultarNome.value}`;
  try {
    const resp = await fetch(url);
    empregados = await resp.json();
    addLog(`Empregado de Nome: ${inputConsultarNome.value} carregados com sucesso`,LogEnum.INFO);
    await reloadEmpregados();
  } catch (e) {
    addLog(`Exception during "readEmpregados()" - ${e}`, LogEnum.DANGER);
  }
}

async function readEmpregadoByCargo()
{
EmpregadosElement.innerHTML = 'carregando...';
  const url = `/api/empregados/cargo/${inputConsultarCargo.value}`;
  try {
    const resp = await fetch(url);
    empregados = await resp.json();
    addLog(`Empregado de Cargo: ${inputConsultarCargo.value} carregados com sucesso`,LogEnum.INFO);
    await reloadEmpregados();
  } catch (e) {
    addLog(`Exception during "readEmpregados()" - ${e}`, LogEnum.DANGER);
  }
}



// =========================
// UPDATE Methods
// =========================
function prepareUpdateForm(id) {
  for(i in empregados) {
    if (empregados[i].id == id) {
      empregadoSelected = empregados[i];
      idUpdateInputEmp.value = empregadoSelected.id;
      nameUpdateInputEmp.value = empregadoSelected.nome;
      cargoUpdateInputEmp.value = empregadoSelected.cargo;
      salarioUpdateInputEmp.value = empregadoSelected.salario;
      return;
    }
  }
  empregadoSelected = null;
}

async function updateEmpregado() {
  if(!empregadoSelected) {
    addLog(`Não é possível atualizar o Empregado. Primeiro vocês deve selecionar um Empregado!`, LogEnum.WARNING);
    return;
  }

  const URL = `/api/empregados/${empregadoSelected.id}`;
  const empData = {
    'nome': nameUpdateInputEmp.value,
    'cargo': cargoUpdateInputEmp.value,
    'salario': salarioUpdateInputEmp.value
  };
  const putRequest = {
    method: 'PUT',
    body: JSON.stringify(empData),
    headers: {
      'Content-type': 'application/json;charset=UTF-8'
    }
  };
  try {
    const resp = await fetch(URL, putRequest);
    if (resp.status == 200) {
      addLog(`Empregado ${empData.nome} atualizado com sucesso`, LogEnum.INFO);
      resetUpdateForm();
      await readEmpregados();
    }
  } catch (e) {
    addLog(`Exception during "updateEmpregado()" - ${e}`, LogEnum.DANGER);
  }
}

// =========================
// DELETE Methods
// =========================
function prepareForDelete(id) {
  for(i in empregados) {
    if (empregados[i].id == id) {
      empregadoSelected = empregados[i];
      empregadoSelectedElement.innerHTML = empregadoSelected.nome;
      return;
    }
  }
  empregadoSelected = null;
}

async function deleteEmpregado() {
  if(!empregadoSelected) {
    addLog(`Não é possível apagar o Empregado. Primeiro vocês deve selecionar um Empregado!`, LogEnum.WARNING);
    return;
  }
  const id = empregadoSelected.id;
  empregadoSelected = null;
  const URL = `/api/empregados/${id}`;
  const deleteRequest = {
    method: 'DELETE'
  };
  try {
    const resp = await fetch(URL, deleteRequest);
    if (resp.status == 200) {
      addLog(`Empregado ${id} apagado com sucesso`, LogEnum.INFO);
      await readEmpregados();
    } else {
      addLog(`Empregado ${id} não encontrado`, LogEnum.WARNING);
    }
  } catch (e) {
    addLog(`Exception during "deleteEmpregado()" - ${e}`, LogEnum.DANGER);
  }
}

// Load Empregados Table on init
readEmpregados();