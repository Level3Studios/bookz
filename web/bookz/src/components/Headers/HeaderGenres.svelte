<script>
  // core components
  import CardGenre from "../Cards/CardGenre.svelte";
</script>

<!-- Header -->
<div class="relative bg-level3blue-500 md:pt-28 pb-32 pt-12">
  <div class="px-4 md:px-10 mx-auto w-full">
    <div>
      <!-- Card stats -->
      <div class="flex flex-wrap">
        <div class="w-full lg:w-6/12 xl:w-3/12 px-4">
          <CardGenre
            cardIndex={0}
            bgColor="bg-cyan-300"
            hoverColor="hover:bg-cyan-400"
            genreTitle="Adventure"
            genreImage="Adventure.png"
          />
        </div>
        <div class="w-full lg:w-6/12 xl:w-3/12 px-4">
          <CardGenre
            cardIndex={1}
            bgColor="bg-orange-300"
            hoverColor="hover:bg-orange-400"
            genreTitle="Fantasy"
            genreImage="Fortnite.png"
          />
        </div>
        <div class="w-full lg:w-6/12 xl:w-3/12 px-4">
          <CardGenre
            cardIndex={2}
            bgColor="bg-emerald-300"
            hoverColor="hover:bg-emerald-400"
            genreTitle="Sci-Fi"
            genreImage="Alien.png"
          />
        </div>
        <div class="w-full lg:w-6/12 xl:w-3/12 px-4">
          <CardGenre
            cardIndex={3}
            bgColor="bg-rose-300"
            hoverColor="hover:bg-rose-400"
            genreTitle="Romance"
            genreImage="Romance.png"
          />
        </div>
      </div>
    </div>
  </div>
</div>
