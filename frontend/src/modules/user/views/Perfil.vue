<template>
  <div>
    <Panel class="shadow-lg">
      <template #header>
        <div class="d-flex justify-content-between w-100">
          <p class="h5"><b>Mi perfil</b></p>
        </div>
      </template>
      <template>
        <b-row>
          <b-col cols="12" md="6" lg="3">
            <b-img src="https://via.placeholder.com/150" thumbnail alt="profile" class="w-100 mb-3"/>
            <div class="d-flex justify-content-center w-100">
              <b-row>
                <b-col cols="12">
                  <h4>Raúl Domínguez Bravo</h4>
                </b-col>
              </b-row>
            </div>
            <b-row>
              <b-col cols="12" class="d-flex justify-content-center">
                <p><label style="font-weight: lighter; font-size: 22px;">Neurocirujano</label></p>
              </b-col>
            </b-row>
          </b-col>
          <b-col cols="12" md="6" lg="9">
            <div class="d-flex justify-content-left" style="border-bottom: 0.5px solid #d3d3d3;">
              <h3>Información profesional</h3>
            </div>
            <div class="d-flex justify-content-left mt-2">
              <b-row>
                <div>
                  <b-col cols="12">
                    <b-form-group
                      id="fieldset-cedula"
                    >
                      
                    </b-form-group>
                  </b-col>
                </div>
              </b-row>
            </div>
          </b-col>
        </b-row>
      </template>
    </Panel>  
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>

</style>