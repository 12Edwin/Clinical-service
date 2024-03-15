<template>
  <div style="text-align:left">
  <div v-for="(treatment, ind) in treats" :style="{'z-index': (99-ind)}"  class="shape" @click="calcHeight(ind)">
    <div class="content-card" :key="ind">
      <div class="header-acc">
        <div class="head"><b> Tratamiento: </b></div>
        <div class="head">{{ treatment.name }}</div>
      </div>
      <div>
      <p>Estudios de laboratorio</p>
      <p>Tratamiento en casa</p>
      <p>Recomendaciones</p>
      </div>
    </div>
  </div>
  </div>
</template>

<script>
export default {

  data (){
    return{
      treats:[
        {name: "name"},{name: "name"},{name: "name"}
      ]
    }
  },
  methods:{
    calcHeight(index){
      let targetDiv = document.getElementsByClassName("shape")[index]
      let contentDiv = document.getElementsByClassName("content-card")[index]
      let finalHeight = contentDiv.clientHeight + 40;
      let initialHeight = targetDiv.clientHeight;
      targetDiv.style.setProperty("--final-height", finalHeight + "px");
      if (!targetDiv.classList.contains("increase")) {
        targetDiv.style.setProperty("--initial-height", initialHeight + "px");
        targetDiv.classList.toggle("increase")
      }else{
        targetDiv.classList.toggle("decrement")
      }

    }
  }
}

</script>

<style scoped>
  .shape{
    padding: 40px 20px 20px;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
    background-color: white;
    height: 90px;
    overflow: hidden;
    text-align: left;
    position: relative;
    animation-name: resize2;
    animation-duration: 0.5s;
    animation-fill-mode: forwards;
    cursor: pointer;
  }

  .shape:not(:first-of-type){
    margin-top: -20px;
  }

  .content-card{
    width: auto;
    box-sizing: content-box;
    display:inline-block;
  }

  .increase{
    animation-name: expand;
    animation-fill-mode: both;
    animation-timing-function: ease-in;
    animation-duration: 0.5s;
  }


  @keyframes expand {
    0%{
      box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
      height: calc(var(--initial-height) + 0px);
    }
    60%{
      height: calc(var(--final-height) - 30px);
    }
    100%{
      height: calc(var(--final-height) + 0px);
      box-shadow: rgb(118, 205, 236, 0.5) 0 8px 12px 0;
    }
  }

  .decrement{
    animation-name: decrease;
    animation-fill-mode: both;
    animation-timing-function: ease-in;
    animation-duration: 0.7s;
  }

  @keyframes decrease {
    0%{
      box-shadow: rgb(118, 205, 236, 0.5) 0 8px 12px 0;
      height: calc(var(--final-height) + 0px);
    }
    60%{
      height: calc(var(--initial-height) + 30px);
    }

    100%{
      height: calc(var(--initial-height) + 0px);
      box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
     }
  }

  .header-acc{
    height: 90px;
  }
  .head{
    display: inline-block;
    margin-right: 10px;
    margin-left: 10px;
    font-size: 14pt;
  }

</style>