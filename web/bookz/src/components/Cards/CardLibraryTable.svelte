<script lang="ts">
  // imports
  import type { Item } from "../../network/BookzInterface";
  import { BookzViewModel } from "../../viewmodels/BookzViewModel";
  import BookDetail from "../../views/BookDetail.svelte";
  import { link } from "svelte-routing";
  import { getContext } from "svelte";
  const { open } = getContext("simple-modal");

  // core components
  let viewModel = BookzViewModel.getInstance();
  let libraryBooks = viewModel.storedLibraryBooks;

  // interactions
  function deleteBook(book: Item) {
    viewModel.removeBookFromLibrary(book);
    libraryBooks = libraryBooks.filter((item) => item.id !== book.id);
  }

  function moveBook(book: Item) {
    viewModel.saveBookToWishlist(book);
    deleteBook(book);
  }
</script>

<div
  class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded bg-white"
>
  <div class="rounded-t mb-0 px-4 py-3 border-0">
    <div class="flex flex-wrap items-center">
      <div class="relative w-full px-4 max-w-full flex-grow flex-1">
        <h3 class="font-semibold text-lg text-slate-700">My Library</h3>
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
        {#await libraryBooks then result}
          {#each result as book}
            <tr>
              <th
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left flex items-center"
              >
                <img
                  src={book.volumeInfo.imageLinks.thumbnail}
                  class="h-12 w-12 bg-white rounded-full border"
                  alt="..."
                />
                <a
                  use:link
                  href="/"
                  on:click={() => open(BookDetail, { book: book })}
                  class="ml-3 font-bold text-slate-600"
                >
                  {book.volumeInfo.title}
                </a>
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
                class="border-t-0 px-6 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
              >
                <ul class="list-outside">
                  <li>
                    <button
                      on:click={() => deleteBook(book)}
                      class="bg-level3blue-500 text-white active:bg-level3blue-600 text-xs font-bold uppercase px-3 py-1 rounded outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                      type="button"
                    >
                      <span><i class="fas fa-ban mr-2" /> Delete Book</span>
                    </button>
                  </li>
                  <li>
                    <button
                      on:click={() => moveBook(book)}
                      class="bg-level3blue-500 text-white active:bg-level3blue-600 text-xs font-bold uppercase px-3 py-1 rounded outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                      type="button"
                    >
                      <span
                        ><i class="fas fa-wand-magic-sparkles mr-2" /> Move to Wishlist</span
                      >
                    </button>
                  </li>
                </ul>
              </td>
            </tr>
          {/each}
        {/await}
      </tbody>
    </table>
  </div>
</div>
