<script lang="ts">
  // core components
  import DashboardDropdown from "../Dropdowns/DashboardDropdown.svelte";
  import { BookzViewModel } from "../../viewmodels/BookzViewModel";
  import type { Item } from "../../network/BookzInterface";
  import { selectedGenre } from "../../helpers/store";

  // local components
  let viewModel = BookzViewModel.getInstance();

  var books: Promise<Item[]>;
  selectedGenre.subscribe((value) => {
    books = viewModel.didSelectGenreCard(value);
  });
</script>

<div
  class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded bg-white"
>
  <div class="rounded-t mb-0 px-4 py-3 border-0">
    <div class="flex flex-wrap items-center">
      <div class="relative w-full px-4 max-w-full flex-grow flex-1">
        <h3 class="font-semibold text-lg text-slate-700">Top 5 Books</h3>
      </div>
    </div>
  </div>
  <div class="block w-full overflow-x-auto">
    <!-- Books table -->
    <table class="items-center w-full bg-transparent border-collapse">
      <thead>
        <tr>
          <th
            class="px-6 align-middle border border-solid py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left bg-slate-50 text-slate-500 border-slate-100"
          >
            Title
          </th>
          <th
            class="px-6 align-middle border border-solid py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left bg-slate-50 text-slate-500 border-slate-100"
          >
            Authors
          </th>
          <th
            class="px-6 align-middle border border-solid py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left bg-slate-50 text-slate-500 border-slate-100"
          >
            Rating
          </th>
          <th
            class="px-6 align-middle border border-solid py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left bg-slate-50 text-slate-500 border-slate-100"
          >
            Published Date
          </th>
          <th
            class="px-6 align-middle border border-solid py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left bg-slate-50 text-slate-500 border-slate-100"
          />
        </tr>
      </thead>
      <tbody>
        {#await books then result}
          {#each result as book}
            <tr>
              <th
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left flex items-center"
              >
                {#await viewModel.getBookImage(book.id) then cover}
                  <img
                    src={cover}
                    class="h-12 w-12 bg-white rounded-full border"
                    alt="..."
                  />
                {/await}
                <span class="ml-3 font-bold text-slate-600">
                  {book.volumeInfo.title}
                </span>
              </th>
              <td
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
              >
                {viewModel.getAuthors(book)}
              </td>
              <td
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
              >
                {viewModel.getRatingValue(book)}
              </td>
              <td
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
              >
                {viewModel.getPublishedDate(book)}
              </td>

              <td
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-right"
              >
                <DashboardDropdown {book} />
              </td>
            </tr>
          {/each}
        {/await}
      </tbody>
    </table>
  </div>
</div>
