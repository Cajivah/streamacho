<template>
  <i
    :class="isOpen && 'caret--inverted'"
    class="caret fa fa-caret-down"
  />
</template>

<script>
export default {
  name: 'CaretDown',
  props: {
    isOpen: {
      type: Boolean,
      required: true
    }
  }
}
</script>

<style scoped>
  .caret {
    transition: 0.2s;
  }

  .caret--inverted {
    transform: rotate(180deg);
  }
</style>