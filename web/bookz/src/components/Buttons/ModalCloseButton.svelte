<script>
  export let onClose;
</script>

<div class="flex flex-grow flex-row-reverse">
  <button
    class="text-2xl pr-3 pt-4 text-level3blue-400 hover:text-level3blue-600"
    on:click={onClose}><i class="fas fa-circle-xmark" /></button
  >
</div>
