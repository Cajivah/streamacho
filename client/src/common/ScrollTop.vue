<template>
  <a
    v-scroll-to="'#top'"
    :class="{'scroll-top--hidden': hidden}"
    role="button"
    class="scroll-top button is-info"
  >
    <i class="fa fa-chevron-up"/>
  </a>
</template>

<script>
export default {
  name: 'ScrollTop',
  data: () => ({
    hidden: true,
  }),
  created() {
    window.addEventListener('scroll', this.handleScroll);
  },
  destroyed() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
      this.hidden = window.scrollY < window.innerHeight;
    }
  }
}
</script>

<style scoped>
  .scroll-top {
    position: fixed;
    bottom: 30px;
    right: 30px;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    font-size: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: 0.2s ease-out;
    opacity: 1;
  }

  .scroll-top--hidden {
    right: 0;
    transition: 0.2s ease-in;
    opacity: 0;
  }
</style>