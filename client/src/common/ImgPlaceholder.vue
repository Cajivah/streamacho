<template>
  <div
    :class="backgroundColor"
    class="picture picture-placeholder is-unselectable"
  >
    {{ string[0] }}
  </div>
</template>

<script>
export default {
  name: 'ImgPlaceholder',
  props: {
    string: {
      type: String,
      required: true
    }
  },
  computed: {
    backgroundColor() {
      return `color-${this.string.charCodeAt(0) % 16}`;
    }
  }
}
</script>

<style scoped>
  .picture-placeholder {
    display: flex;
    justify-content: center;
    align-items: center;
    text-transform: uppercase;
  }

  .color-0 {
    background: #EF5350;
    color: white;
  }

  .color-1 {
    background: #EC407A;
    color: white;
  }

  .color-2 {
    background: #AB47BC;
    color: white;
  }

  .color-3 {
    background: #7E57C2;
    color: white;
  }

  .color-4 {
    background: #5C6BC0;
    color: white;
  }

  .color-5 {
    background: #42A5F5;
  }

  .color-6 {
    background: #29B6F6;
  }

  .color-7 {
    background: #26C6DA;
  }

  .color-8 {
    background: #26A69A;
  }

  .color-9 {
    background: #66BB6A;
  }

  .color-10 {
    background: #9CCC65;
  }

  .color-11 {
    background: #D4E157;
  }

  .color-12 {
    background: #FFEE58;
  }

  .color-13 {
    background: #FFCA28;
  }

  .color-14 {
    background: #FFA726;
  }

  .color-15 {
    background-color: #FF7043;
  }

  .color-16 {
    background: #8D6E63;
    color: white;
  }
</style>