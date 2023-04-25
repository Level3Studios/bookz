<script lang="ts">
  import { link } from "svelte-routing";
  import { selectedView } from "../../helpers/store";

  let collapseShow = "hidden";

  function toggleCollapseShow(classes: string) {
    collapseShow = classes;
  }

  var viewId = 0;
  selectedView.subscribe((value: number) => {
    viewId = value;
  });
  function didSelectView(view: number) {
    selectedView.set(view);
    toggleCollapseShow("hidden");
  }
</script>

<nav
  class="md:left-0 md:block md:fixed md:top-0 md:bottom-0 md:overflow-y-auto md:flex-row md:flex-nowrap md:overflow-hidden shadow-xl bg-white flex flex-wrap items-center justify-between relative md:w-64 z-10 py-4 px-6"
>
  <div
    class="md:flex-col md:items-stretch md:min-h-full md:flex-nowrap px-0 flex flex-wrap items-center justify-start w-full mx-auto"
  >
    <!-- Toggler -->
    <button
      class="cursor-pointer text-level3blue-500 opacity-50 md:hidden px-3 py-1 text-xl leading-none bg-transparent rounded border border-solid border-transparent"
      type="button"
      on:click={() => toggleCollapseShow("bg-white m-2 py-3 px-6")}
    >
      <i class="fas fa-bars" />
    </button>
    <!-- Brand -->
    <a
      use:link
      class="md:block text-left md:pb-2 text-level3blue-600 inline-block whitespace-nowrap text-sm uppercase font-bold p-4 px-0"
      href="/"
      on:click={() => didSelectView(0)}
    >
      Bookz
    </a>
    <!-- Collapse -->
    <div
      class="md:flex md:flex-col md:items-stretch md:opacity-100 md:relative md:mt-4 md:shadow-none shadow absolute top-0 left-0 right-0 z-40 overflow-y-auto overflow-x-hidden h-auto items-center flex-1 rounded {collapseShow}"
    >
      <!-- Collapse header -->
      <div
        class="md:min-w-full md:hidden block pb-4 mb-4 border-b border-solid border-slate-300"
      >
        <div class="flex flex-wrap">
          <div class="w-6/12">
            <a
              use:link
              class="md:block text-left md:pb-2 text-level3blue-600 mr-0 inline-block whitespace-nowrap text-sm uppercase font-bold p-4 px-0"
              href="/"
              on:click={() => didSelectView(0)}
            >
              Bookz
            </a>
          </div>
          <div class="w-6/12 flex justify-end">
            <button
              type="button"
              class="cursor-pointer text-level3blue-500 opacity-50 md:hidden px-3 py-1 text-xl leading-none bg-transparent rounded border border-solid border-transparent"
              on:click={() => toggleCollapseShow("hidden")}
            >
              <i class="fas fa-times" />
            </button>
          </div>
        </div>
      </div>

      <!-- Navigation -->

      <ul class="md:flex-col md:min-w-full flex flex-col list-none">
        <li class="items-center">
          <a
            use:link
            href="/"
            on:click={() => didSelectView(0)}
            class="text-xs uppercase py-3 font-bold block {viewId === 0
              ? 'text-cyan-500 hover:text-cyan-600'
              : 'text-level3blue-700 hover:text-level3blue-500'}"
          >
            <i
              class="fas fa-tv mr-2 text-sm {viewId === 0
                ? 'opacity-75'
                : 'text-level3blue-300'}"
            />
            Dashboard
          </a>
        </li>

        <li class="items-center">
          <a
            use:link
            href="/"
            on:click={() => didSelectView(1)}
            class="text-xs uppercase py-3 font-bold block {viewId === 1
              ? 'text-cyan-500 hover:text-cyan-600'
              : 'text-level3blue-700 hover:text-level3blue-500'}"
          >
            <i
              class="fas fa-book mr-2 text-sm {viewId === 1
                ? 'opacity-75'
                : 'text-level3blue-300'}"
            />
            Library
          </a>
        </li>

        <li class="items-center">
          <a
            use:link
            href="/"
            on:click={() => didSelectView(2)}
            class="text-xs uppercase py-3 font-bold block {viewId === 2
              ? 'text-cyan-500 hover:text-cyan-600'
              : 'text-level3blue-700 hover:text-level3blue-500'}"
          >
            <i
              class="fas fa-wand-magic-sparkles mr-2 text-sm {viewId === 2
                ? 'opacity-75'
                : 'text-level3blue-300'}"
            />
            Wishlist
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>
