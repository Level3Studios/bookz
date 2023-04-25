<script>
  import { onMount } from "svelte";
  import { selectedGenre } from "../helpers/store";
  // core components
  import CardDashboardTable from "../components/Cards/CardDashboardTable.svelte";

  onMount(() => {
    selectedGenre.set(0);
  });
</script>

<div>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardDashboardTable />
    </div>
  </div>
</div>
