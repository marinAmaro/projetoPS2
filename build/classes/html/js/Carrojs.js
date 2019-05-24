// Get Input Elements
const idInputCar       = document.getElementById('idInputCar');
const modeloInputCar     = document.getElementById('modeloInputCar');
const marcaInputCar =    document.getElementById('marcaInputCar');
const anoInputCar =    document.getElementById('anoInputCar');
const categoriaInputCar =    document.getElementById('categoriaInputCar');
const idUpdateInputCar       = document.getElementById('idUpdateInputCar');
const modeloUpdateInputCar     = document.getElementById('modeloUpdateInputCar');
const marcaUpdateInputCar     = document.getElementById('marcaUpdateInputCar');
const anoUpdateInputCar     = document.getElementById('anoUpdateInputCar');
const categoriaUpdateInputCar     = document.getElementById('categoriaUpdateInputCar');

const inputConsultarId     = document.getElementById('inputConsultarId');
const inputConsultarAno    = document.getElementById('inputConsultarAno');
const inputConsultarModelo     = document.getElementById('inputConsultarModelo');

// Get Content Elements
const loggerElement = document.getElementById('logger');
const CarsElement = document.getElementById('carros');
const carSelectedElement = document.getElementById('carSelected');

// Store fetched teachers
var cars = [];
var carSelected = null;

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

function reloadCars() {
  var listaCars = '';
  var n = cars.length;
  for (var i = 0; i < n; i++) {
    listaCars += makeApplicationRow(cars[i]);
  }
  CarsElement.innerHTML = listaCars;
    
    inputConsultarId.value = '';
    inputConsultarAno.value = '';
    inputConsultarModelo.value = '';
}

function makeApplicationRow(carro) {
  return `<tr><th scope="row">${carro.id}</th>
          <td>${carro.modelo}</td><td>${carro.marca}</td><td>${carro.ano}</td><td>${carro.categoria}</td>
          <td class="text-center">
            <button type="button" class="btn btn-warning" onClick="prepareUpdateForm(${carro.id})" data-toggle="modal" data-target="#updateModal">editar</button>
            <button type="button" class="btn btn-danger" onClick="prepareForDelete(${carro.id})" data-toggle="modal" data-target="#deleteModal">apagar</button>
          </td>`;
}

function resetCreateForm() {
  idInputCar.value = '';
  modeloInputCar.value = '';
  marcaInputCar.value = '';
  anoInputCar.value = '';
  categoriaInputCar.value = '';
}

function resetUpdateForm() {
  idUpdateInputCar.value = '';
  modeloUpdateInputCar.value = '';
  marcaUpdateInputCar.value = '';
  anoUpdateInputCar.value = '';
  categoriaUpdateInputCar.value = '';
  carSelected = null;
}

function resetDeleteModal() {
  deleteTeacher.innerHTML = "";
  carSelected = null;
}


// =========================
// CREATE Methods
// =========================
async function createCar() {
  const URL = `/api/carros`;
  const CarData = {
    'modelo': modeloInputCar.value,
    'marca': marcaInputCar.value,
    'ano': anoInputCar.value,
    'categoria': categoriaInputCar.value
  };
  const postRequest = {
    method: 'POST',
    body: JSON.stringify(CarData),
    headers: {
      'Content-type': 'application/json;charset=UTF-8'
    }
  };
  try {
    const resp = await fetch(URL, postRequest);
    if (resp.status == 200) {
      addLog(`Carro ${CarData.nome} criado com sucesso`, LogEnum.INFO);
      await resetCreateForm();
      await readCars();
    } else {
      addLog(`Criar Carro: respota diferente de 200 - ${resp.status}`, LogEnum.WARNING);
      await readCars();
    }
  } catch (e) {
    addLog(`Exception during "createCar()" - ${e}`, LogEnum.DANGER);
  }
}

// =========================
// READ Methods
// =========================
async function readCars() {
  CarsElement.innerHTML = 'carregando...';
  const url = `/api/carros`;
  try {
    const resp = await fetch(url);
    cars = await resp.json();
    addLog('Carros carregados com sucesso',LogEnum.INFO);
    await reloadCars();
  } catch (e) {
    addLog(`Exception during "readCars()" - ${e}`, LogEnum.DANGER);
  }
}

async function readCarById()
{
CarsElement.innerHTML = 'carregando...';
  const url = `/api/carros/id/${inputConsultarId.value}`;
  try {
    const resp = await fetch(url);
    cars = await resp.json();
    addLog(`Carro de Id: ${inputConsultarId.value} carregado com sucesso`,LogEnum.INFO);
    await reloadCars();
  } catch (e) {
    addLog(`Exception during "readCars()" - ${e}`, LogEnum.DANGER);
  }
}

async function readCarByAno()
{
CarsElement.innerHTML = 'carregando...';
  const url = `/api/carros/ano/${inputConsultarAno.value}`;
  try {
    const resp = await fetch(url);
    cars = await resp.json();
    addLog(`Aplicativo de Nome: ${inputConsultarAno.value} carregados com sucesso`,LogEnum.INFO);
    await reloadCars();
  } catch (e) {
    addLog(`Exception during "readApplicationsByNome()" - ${e}`, LogEnum.DANGER);
  }
}

async function readCarByModelo()
{
CarsElement.innerHTML = 'carregando...';
  const url = `/api/carros/modelo/${inputConsultarModelo.value}`;
  try {
    const resp = await fetch(url);
    cars = await resp.json();
    addLog(`Aplicativo de Desenvolvedor: ${inputConsultarModelo.value} carregados com sucesso`,LogEnum.INFO);
    await reloadCars();
  } catch (e) {
    addLog(`Exception during "readApplicationsByDev()" - ${e}`, LogEnum.DANGER);
  }
}



// =========================
// UPDATE Methods
// =========================
function prepareUpdateForm(id) {
  for(i in cars) {
    if (cars[i].id == id) {
      carSelected = cars[i];
      idUpdateInputCar.value = carSelected.id;
      modeloUpdateInputCar.value = carSelected.modelo;
      marcaUpdateInputCar.value = carSelected.marca;
      anoUpdateInputCar.value = carSelected.ano;
	  categoriaUpdateInputCar.value = carSelected.categoria;
      return;
    }
  }
  carSelected = null;
}

async function updateCar() {
  if(!carSelected) {
    addLog(`Não é possível atualizar o CArro. Primeiro vocês deve selecionar um Carro!`, LogEnum.WARNING);
    return;
  }

  const URL = `/api/carros/${carSelected.id}`;
  const carData = {
    'modelo': modeloUpdateInputCar.value,
    'marca': marcaUpdateInputCar.value,
    'ano': anoUpdateInputCar.value,
	'categoria': categoriaUpdateInputCar.value
  };
  const putRequest = {
    method: 'PUT',
    body: JSON.stringify(carData),
    headers: {
      'Content-type': 'application/json;charset=UTF-8'
    }
  };
  try {
    const resp = await fetch(URL, putRequest);
    if (resp.status == 200) {
      addLog(`Carro ${carData.modelo} atualizado com sucesso`, LogEnum.INFO);
      resetUpdateForm();
      await readCars();
    }
  } catch (e) {
    addLog(`Exception during "updateCar()" - ${e}`, LogEnum.DANGER);
  }
}

// =========================
// DELETE Methods
// =========================
function prepareForDelete(id) {
  for(i in cars) {
    if (cars[i].id == id) {
      carSelected = cars[i];
      carSelectedElement.innerHTML = carSelected.modelo;
      return;
    }
  }
  carSelected = null;
}

async function deleteCar() {
  if(!carSelected) {
    addLog(`Não é possível apagar o Carro. Primeiro vocês deve selecione um Carro!`, LogEnum.WARNING);
    return;
  }
  const id = carSelected.id;
  carSelected = null;
  const URL = `/api/carros/${id}`;
  const deleteRequest = {
    method: 'DELETE'
  };
  try {
    const resp = await fetch(URL, deleteRequest);
    if (resp.status == 200) {
      addLog(`Carro ${id} apagado com sucesso`, LogEnum.INFO);
      await readCars();
    } else {
      addLog(`Carro ${id} não encontrado`, LogEnum.WARNING);
    }
  } catch (e) {
    addLog(`Exception during "deleteCar()" - ${e}`, LogEnum.DANGER);
  }
}

// Load Cars Table on init
readCars();