<script lang="ts">
  // imports
  import { onMount } from "svelte";
  import { createPopper } from "@popperjs/core";
  import type { Item } from "../../network/BookzInterface";
  import { BookzViewModel } from "../../viewmodels/BookzViewModel";
  import BookDetail from "../../views/BookDetail.svelte";
  import { getContext } from "svelte";
  const { open } = getContext("simple-modal");
  // core components
  export let book: Item;
  let viewModel = BookzViewModel.getInstance();

  // drop down components
  let dropdownPopoverShow = false;
  let btnDropdownRef: Element;
  let popoverDropdownRef: HTMLElement;

  const toggleDropdown = (event: MouseEvent) => {
    event.preventDefault();
    if (dropdownPopoverShow) {
      dropdownPopoverShow = false;
    } else {
      dropdownPopoverShow = true;
      createPopper(btnDropdownRef, popoverDropdownRef, {
        placement: "bottom-start",
      });
    }
  };

  // detail view
  const showPopup = () => open(BookDetail, { book: book });
  function showBookDetail(event: MouseEvent) {
    showPopup();
    toggleDropdown(event);
  }

  // book components
  let allSavedBooks: string[] = [];
  onMount(() => {
    updateSaveList();
  });

  function updateSaveList() {
    let libraryBooks = viewModel.storedLibraryBooks.map((item) => item.id);
    let wishlistBooks = viewModel.storedWishlistBooks.map((item) => item.id);
    allSavedBooks = libraryBooks.concat(wishlistBooks);
  }

  function saveToLibrary(event: MouseEvent) {
    viewModel.saveBookToLibrary(book);
    updateSaveList();
    toggleDropdown(event);
  }

  function saveToWishlist(event: MouseEvent) {
    viewModel.saveBookToWishlist(book);
    updateSaveList();
    toggleDropdown(event);
  }
</script>

<div>
  <a
    class="text-slate-500 py-1 px-3"
    href="#actions"
    bind:this={btnDropdownRef}
    on:click={toggleDropdown}
  >
    <i class="fas fa-ellipsis-v" />
  </a>
  <div
    bind:this={popoverDropdownRef}
    class="bg-white text-base z-50 float-left py-2 list-none text-left rounded shadow-lg min-w-48 {dropdownPopoverShow
      ? 'block'
      : 'hidden'}"
  >
    <a
      href="#details"
      on:click={(e) => showBookDetail(e)}
      class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-slate-700"
    >
      View Details
    </a>
    {#if allSavedBooks.includes(book.id) === false}
      <a
        href="#library"
        on:click={(e) => saveToLibrary(e)}
        class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-slate-700"
      >
        Save to Library
      </a>
      <a
        href="#wishlist"
        on:click={(e) => saveToWishlist(e)}
        class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-slate-700"
      >
        Save to Wishlist
      </a>
    {/if}
  </div>
</div>
