<script lang="ts">
  // imports
  import type { Item } from "../network/BookzInterface";
  import { BookzViewModel } from "../viewmodels/BookzViewModel";
  // core components
  const viewModel = BookzViewModel.getInstance();
  export let book: Item;
</script>

<div
  class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg mt-16"
>
  <div class="px-6">
    <div class="flex flex-wrap justify-center">
      <div class="w-full px-4 flex justify-center">
        <div class="relative">
          <img
            alt="book cover"
            src={book.volumeInfo.imageLinks.thumbnail}
            class="shadow-xl rounded-full h-auto align-middle border-none absolute -m-16 -ml-20 lg:-ml-16 max-w-150-px"
          />
        </div>
      </div>
      <div class="w-full px-4 text-center mt-32">
        <div class="flex justify-center py-4 lg:pt-4 pt-8">
          <div class="mr-4 p-3 text-center">
            <span
              class="text-xl font-bold block uppercase tracking-wide text-slate-600"
            >
              {viewModel.getRatingValue(book)}
            </span>
            <span class="text-sm text-slate-400">Rating</span>
          </div>
          <div class="mr-4 p-3 text-center">
            <span
              class="text-xl font-bold block uppercase tracking-wide text-slate-600"
            >
              {book.volumeInfo.pageCount}
            </span>
            <span class="text-sm text-slate-400">Pages</span>
          </div>
          <div class="lg:mr-4 p-3 text-center">
            <span
              class="text-xl font-bold block uppercase tracking-wide text-slate-600"
            >
              {viewModel.fixBookRating(book)}
            </span>
            <span class="text-sm text-slate-400">Rating</span>
          </div>
        </div>
      </div>
    </div>
    <div class="text-center mt-4">
      <h3 class="text-2xl font-semibold leading-normal text-slate-700 mb-2">
        {book.volumeInfo.title}
      </h3>
      <div
        class="text-sm leading-normal mt-0 mb-2 text-slate-400 font-bold uppercase"
      >
        <i class="fas fa-user-group mr-2 text-lg text-slate-400" />
        {viewModel.getAuthors(book)}
      </div>
      <div class="text-sm mb-2 text-slate-600">
        <i class="fas fa-calendar-alt mr-2 text-lg text-slate-400" />
        {viewModel.getPublishedDate(book)}
      </div>
      <div class="text-sm mb-2 text-slate-600">
        <i class="fas fa-layer-group mr-2 text-lg text-slate-400" />
        {viewModel.getCategories(book)}
      </div>
    </div>
    <div class="mt-10 py-10 border-t border-slate-200 text-center">
      <div class="flex flex-wrap justify-center">
        <div class="w-full lg:w-9/12 px-4">
          <p class="mb-4 text-lg leading-relaxed text-slate-700">
            {book.volumeInfo.description}
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
