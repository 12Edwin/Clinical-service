<template>
    <panel>
        <template #header>
            <h5 class="d-flex w-100 align-items-center">Registro de doctores
            </h5>
        </template>
        <div class="media text-muted pt-3">
            <div class="row">
                <div class="col text-left">
                    <h5 class="border-none pb-2 mb-2">Información personal</h5>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-1">
                        <input type="text" class="form-control" id="input-name" placeholder="Raul">
                        <label for="input-name">Nombre</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="input-surname" placeholder="Dominguez">
                        <label for="input-surname">Apellido materno</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="input-lastname" placeholder="Dominguez">
                        <label for="input-lastname">Apellido paterno</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="date" class="form-control" id="input-birthdate" placeholder="Dominguez">
                        <label for="input-birthdate">Fecha de nacimiento</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="input-address" placeholder="Av Mor y Pavón">
                        <label for="input-address">Dirección</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="number" class="form-control" id="input-phone" placeholder="Av Mor y Pavón">
                        <label for="input-phone">Número de teléfono</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="email" class="form-control" id="input-email" placeholder="Av Mor y Pavón">
                        <label for="input-email">Correo electrónico</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating">
                        <select class="form-select text-secondary" id="floatingSelect" placeholder="Género">
                            <option selected>Selecciona una opción</option>
                            <option value="1">Masculino</option>
                            <option value="2">Femenino</option>
                            <option value="3">Otro</option>
                        </select>
                        <label for="floatingSelect">Género</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <h5 class="border-none pb-2 mt-2 text-left">Información profesional</h5>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="input-licence" placeholder="121223">
                        <label for="input-licence">Licencia médica</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="input-speciality" placeholder="Traumatologo">
                        <label for="input-speciality">Especialidad</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <textarea class="form-control" placeholder="Leave a comment here" id="input-experience"></textarea>
                        <label for="input-experience">Experiencia laboral</label>
                    </div>
                </div>
                <div class="col col-lg-3 col-md-6 col-sm-12">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="input-association" placeholder="IMSS">
                        <label for="input-association">Afiliación a asociaciones médicas</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <h5 class="border-none pb-2 mt-2 text-left">Documentación</h5>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="input-group mb-2">
                        <input type="file" class="form-control" id="input-medical-licence">
                        <label class="input-group-text" for="input-medical-licence">Copia licencia médica </label>
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <input type="file" class="form-control" id="input-medical-curriculum">
                        <label class="input-group-text" for="input-medical-curriculum">Currículum</label>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <hr>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                        <button class="btn btn-outline-secondary me-md-2" type="button">Cancelar</button>
                        <button class="btn btn-outline-success" type="button">Guardar</button>
                    </div>
                </div>
            </div>
        </div>
    </panel>
</template>

<script>
export default {

}
</script>

<style></style>