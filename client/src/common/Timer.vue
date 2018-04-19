<template>
  <p>{{ hours }}:{{ minutes }}:{{ seconds }}</p>
</template>

<script>
export default {
  name: 'Timer',
  props: {
    initialValue: {
      type: Number,
      default: 0
    }
  },
  data: () => ({
    timer: null,
    counter: 0,
  }),
  computed: {
    hours() {
      return this.fillWithZero(Math.trunc(this.counter / 3600) % 24);
    },
    minutes() {
      return this.fillWithZero(Math.trunc(this.counter / 60) % 60);
    },
    seconds() {
      return this.fillWithZero(Math.trunc(this.counter % 60));
    }
  },
  created() {
    this.startTimer()
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  methods: {
    startTimer() {
      this.counter = this.initialValue;
      this.timer = setInterval(() => this.counter++, 1000);
    },
    fillWithZero(value) {
      return value.toString().length === 2
        ? value
        : '0' + value;
    }
  }
};
</script>

<style scoped>

</style>