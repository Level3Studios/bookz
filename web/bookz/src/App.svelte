<script lang="ts">
  import { selectedView } from "./helpers/store";
  import Modal from "svelte-simple-modal";
  import ModalCloseButton from "./components/Buttons/ModalCloseButton.svelte";
  //core items
  import Sidebar from "./components/Sidebar/Sidebar.svelte";
  import HeaderGenres from "./components/Headers/HeaderGenres.svelte";
  import Dashboard from "./views/Dashboard.svelte";
  import Library from "./views/Library.svelte";
  import Wishlist from "./views/Wishlist.svelte";
  import FooterBookz from "./components/Footers/FooterBookz.svelte";

  var view = Dashboard;
  selectedView.subscribe((value: number) => {
    switch (value) {
      case 0:
        view = Dashboard;
        break;
      case 1:
        view = Library;
        break;
      case 2:
        view = Wishlist;
        break;
      default:
        view = Dashboard;
        break;
    }
  });
</script>

<main>
  <Modal closeButton={ModalCloseButton}>
    <Sidebar />
    <div class="relative md:ml-64 bg-slate-100">
      <HeaderGenres />
      <div
        class="px-4 md:px-10 mx-auto w-full -m-24 flex flex-col min-h-screen-70"
      >
        <svelte:component this={view} />
        <FooterBookz />
      </div>
    </div>
  </Modal>
</main>
