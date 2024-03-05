<template>
  <div>
    <Panel class="shadow-lg">
      <template #header>
        <div class="d-flex justify-content-between w-100">
          <p class="h5"><b>Mi perfil</b></p>
        </div>
      </template>
      <div class="p-d-flex w-100">
        <b-row>
          <b-col cols="12" md="6" lg="3">
            <b-img src="https://via.placeholder.com/150" thumbnail alt="profile" class="w-100 mb-2"/>
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
            <b-row>
              <b-col cols="12" class="d-flex justify-content-left" style="border-bottom:0.2px solid #d3d3d3; margin-bottom: 10px;">
                <h5>Información personal</h5>
              </b-col>
            </b-row>
           <div >
            <b-row>
              <b-col cols="12" md="6" lg="4">
                <b-form-group
                  id="field-set"
                  class="d-flex justify-content-left"
                >
                   <p><b>Cédula: </b>1912PX88RZ</p>
                </b-form-group>
              </b-col>
              <b-col cols="12" md="6" lg="4">
                <b-form-group
                  id="field-set"
                  class="d-flex justify-content-left"
                >
                   <p><b>Universidad: </b>UAEM</p>
                </b-form-group>
              </b-col>
              <b-col cols="12" md="6" lg="4">
                <b-form-group
                  id="field-set"
                  class="d-flex justify-content-left"
                >
                   <p><b>Universidad: </b>UNAM</p>
                </b-form-group>
              </b-col>
            </b-row>
           </div>
          </b-col>
        </b-row>
      </div>
    </Panel>  
  </div>
</template>

<script>

export default {
}
</script>

<style lang="scss" scoped>

</style>