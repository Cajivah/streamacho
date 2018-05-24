<template>
  <div>
    <div v-if="url">
      <img
        :src="url"
        :style="{ width: size + 'px', height: size + 'px' }"
        class="rounded image"
        alt="Logo"
      >
    </div>
    <div v-else>
      <div
        :class="backgroundColor"
        :style="{ width: size + 'px', height: size + 'px' }"
        class="rounded picture-placeholder is-unselectable"
      >
        {{ string[0] }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ImgPlaceholder',
  props: {
    string: {
      type: String,
      required: true
    },
    url: {
      type: String,
      default: null
    },
    size: {
      type: Number,
      default: 96
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
  .image {
    background-color: white;
  }

  .rounded {
    border-radius: 50%;
    border: solid 2px white;
  }

  .picture-placeholder {
    display: flex;
    justify-content: center;
    align-items: center;
    text-transform: uppercase;
    font-size: 60px;
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