// Get Input Elements
const idInputApp       = document.getElementById('idInputApp');
const nameInputApp     = document.getElementById('nameInputApp');
const devInputApp =    document.getElementById('devInputApp');
const numDownInputApp =    document.getElementById('numDownInputApp');
const idUpdateInputApp       = document.getElementById('idUpdateInputApp');
const nameUpdateInputApp     = document.getElementById('nameUpdateInputApp');
const devUpdateInputApp     = document.getElementById('devUpdateInputApp');
const numDownUpdateInputApp     = document.getElementById('numDownUpdateInputApp');
const inputConsultarId = document.getElementById('inputConsultarId');
const inputConsultarNome = document.getElementById('inputConsultarNome');
const inputConsultarDev = document.getElementById('inputConsultarDev');

// Get Content Elements
const loggerElement = document.getElementById('logger');
const ApplicationsElement = document.getElementById('aplicativos');
const applicationSelectedElement = document.getElementById('applicationSelected');

// Store fetched teachers
var applications = [];
var applicationSelected = null;

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

function reloadApplications() {
  var listaApps = '';
  var n = applications.length;
    if(n == null)
    {
    addLog(`variável applications Nula.`,LogEnum.INFO);
    }
  for (var i = 0; i < n; i++) {
    listaApps += makeApplicationRow(applications[i]);
  }
  ApplicationsElement.innerHTML = listaApps;
    
    inputConsultarId.value = '';
    inputConsultarNome.value = '';
    inputConsultarDev.value = '';
}

function makeApplicationRow(aplicativo) {
  return `<tr><th scope="row">${aplicativo.id}</th>
          <td>${aplicativo.nome}</td><td>${aplicativo.desenvolvedor}</td><td>${aplicativo.numDownloads}</td>
          <td class="text-center">
            <button type="button" class="btn btn-warning" onClick="prepareUpdateForm(${aplicativo.id})" data-toggle="modal" data-target="#updateModal">editar</button>
            <button type="button" class="btn btn-danger" onClick="prepareForDelete(${aplicativo.id})" data-toggle="modal" data-target="#deleteModal">apagar</button>
          </td>`;
}

function resetCreateForm() {
  idInputApp.value = '';
  nameInputApp.value = '';
  devInputApp.value = '';
 numDownInputApp.value = '';
}

function resetUpdateForm() {
  idUpdateInputApp.value = '';
  nameUpdateInputApp.value = '';
  devUpdateInputApp.value = '';
    numDownUpdateInputApp.value = '';
  applicationSelected = null;
}

function resetDeleteModal() {
  deleteTeacher.innerHTML = "";
  applicationSelected = null;
}


// =========================
// CREATE Methods
// =========================
async function createApplication() {
  const URL = `/api/aplicativos`;
  const ApplicationData = {
    'nome': nameInputApp.value,
    'desenvolvedor': devInputApp.value,
    'numDownloads': numDownInputApp.value  
  };
  const postRequest = {
    method: 'POST',
    body: JSON.stringify(ApplicationData),
    headers: {
      'Content-type': 'application/json;charset=UTF-8'
    }
  };
  try {
    const resp = await fetch(URL, postRequest);
    if (resp.status == 200) {
      addLog(`Aplicativo ${ApplicationData.nome} criado com sucesso`, LogEnum.INFO);
      await resetCreateForm();
      await readApplications();
    } else {
      addLog(`Criar Aplicativo: respota diferente de 200 - ${resp.status}`, LogEnum.WARNING);
      await readApplications();
    }
  } catch (e) {
    addLog(`Exception during "createApplication()" - ${e}`, LogEnum.DANGER);
  }
}

// =========================
// READ Methods
// =========================
async function readApplications() {
  ApplicationsElement.innerHTML = 'carregando...';
  const url = `/api/aplicativos`;
  try {
    const resp = await fetch(url);
    applications = await resp.json();
    addLog('Aplicativos carregados com sucesso',LogEnum.INFO);
    await reloadApplications();
  } catch (e) {
    addLog(`Exception during "readApplications()" - ${e}`, LogEnum.DANGER);
  }
}

async function readApplicationById()
{
ApplicationsElement.innerHTML = 'carregando...';
  const url = `/api/aplicativos/id/${inputConsultarId.value}`;
  try {
    const resp = await fetch(url);
    applications = await resp.json();
    addLog(`Aplicativo de Id: ${inputConsultarId.value} carregados com sucesso`,LogEnum.INFO);
    await reloadApplications();
  } catch (e) {
    addLog(`Exception during "readApplicationsById()" - ${e}`, LogEnum.DANGER);
  }
}

async function readApplicationByNome()
{
ApplicationsElement.innerHTML = 'carregando...';
  const url = `/api/aplicativos/nome/${inputConsultarNome.value}`;
  try {
    const resp = await fetch(url);
    applications = await resp.json();
    addLog(`Aplicativo de Nome: ${inputConsultarNome.value} carregados com sucesso`,LogEnum.INFO);
    await reloadApplications();
  } catch (e) {
    addLog(`Exception during "readApplicationsByNome()" - ${e}`, LogEnum.DANGER);
  }
}

async function readApplicationByDev()
{
ApplicationsElement.innerHTML = 'carregando...';
  const url = `/api/aplicativos/desenvolvedor/${inputConsultarDev.value}`;
  try {
    const resp = await fetch(url);
    applications = await resp.json();
    addLog(`Aplicativo de Desenvolvedor: ${inputConsultarDev.value} carregados com sucesso`,LogEnum.INFO);
    await reloadApplications();
  } catch (e) {
    addLog(`Exception during "readApplicationsByDev()" - ${e}`, LogEnum.DANGER);
  }
}


// =========================
// UPDATE Methods
// =========================
function prepareUpdateForm(id) {
  for(i in applications) {
    if (applications[i].id == id) {
      applicationSelected = applications[i];
      idUpdateInputApp.value = applicationSelected.id;
      nameUpdateInputApp.value = applicationSelected.nome;
      devUpdateInputApp.value = applicationSelected.desenvolvedor;
      numDownUpdateInputApp.value = applicationSelected.numDownloads;
      return;
    }
  }
  applicationSelected = null;
}

async function updateApplication() {
  if(!applicationSelected) {
    addLog(`Não é possível atualizar o Aplicativo. Primeiro vocês deve selecionar um Aplicativo!`, LogEnum.WARNING);
    return;
  }

  const URL = `/api/aplicativos/${applicationSelected.id}`;
  const appData = {
    'nome': nameUpdateInputApp.value,
    'desenvolvedor': devUpdateInputApp.value,
    'numDownloads': numDownUpdateInputApp.value
  };
  const putRequest = {
    method: 'PUT',
    body: JSON.stringify(appData),
    headers: {
      'Content-type': 'application/json;charset=UTF-8'
    }
  };
  try {
    const resp = await fetch(URL, putRequest);
    if (resp.status == 200) {
      addLog(`Aplicativo ${appData.nome} atualizado com sucesso`, LogEnum.INFO);
      resetUpdateForm();
      await readApplications();
    }
  } catch (e) {
    addLog(`Exception during "updateApplication()" - ${e}`, LogEnum.DANGER);
  }
}

// =========================
// DELETE Methods
// =========================
function prepareForDelete(id) {
  for(i in applications) {
    if (applications[i].id == id) {
      applicationSelected = applications[i];
      applicationSelectedElement.innerHTML = applicationSelected.nome;
      return;
    }
  }
  applicationSelected = null;
}

async function deleteApplication() {
  if(!applicationSelected) {
    addLog(`Não é possível apagar o professor. Primeiro vocês deve selecione um professor!`, LogEnum.WARNING);
    return;
  }
  const id = applicationSelected.id;
  applicationSelected = null;
  const URL = `/api/aplicativos/${id}`;
  const deleteRequest = {
    method: 'DELETE'
  };
  try {
    const resp = await fetch(URL, deleteRequest);
    if (resp.status == 200) {
      addLog(`Aplicativo ${id} apagado com sucesso`, LogEnum.INFO);
      await readApplications();
    } else {
      addLog(`Aplicativo ${id} não encontrado`, LogEnum.WARNING);
    }
  } catch (e) {
    addLog(`Exception during "deleteTeacher()" - ${e}`, LogEnum.DANGER);
  }
}

// Load Teachers Table on init
readApplications();