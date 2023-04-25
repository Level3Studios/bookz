<script lang="ts">
  import { link } from "svelte-routing";
  import { selectedGenre } from "../../helpers/store";
  // core components
  export let cardIndex = 0;
  export let bgColor = "bg-cyan-300";
  export let hoverColor = "hover:bg-cyan-500";
  export let genreTitle = "Adventure";
  export let genreImage = "Adventure.png";

  function didSelectGenre(id: number) {
    selectedGenre.set(id);
  }
</script>

<a use:link href="/" on:click={() => didSelectGenre(cardIndex)}>
  <div
    class="relative flex flex-col min-w-0 break-words rounded mb-4 xl:mb-0 shadow-lg {bgColor} {hoverColor}"
  >
    <div class="flex-auto p-4">
      <div class="flex flex-wrap">
        <div class="relative w-full py-8 pr-4 max-w-full flex-grow flex-1">
          <span class="font-semibold text-2xl text-slate-100">
            {genreTitle}
          </span>
        </div>
        <div class="relative w-auto pl-4 flex-initial">
          <div class=" p-3 inline-flex items-center justify-center w-28 h-20">
            <img src="assets/img/{genreImage}" alt="genre icon" />
          </div>
        </div>
      </div>
    </div>
  </div>
</a>
