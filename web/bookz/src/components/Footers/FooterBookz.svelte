<script lang="ts">
  // make dynamic date to be added to footer
  let date = new Date().getFullYear();
  let releaseDate = new Date("2023-04-01").getFullYear();
  var dateString = `${releaseDate}`;
  if (releaseDate < date) {
    dateString = `${releaseDate} - ${date}`;
  }
  class FooterPair {
    label: string;
    link: string;

    constructor(label: string, link: string) {
      this.label = label;
      this.link = link;
    }
  }
  const footerLinks: FooterPair[] = [
    new FooterPair(
      "Notus Svelte",
      "https://demos.creative-tim.com/notus-svelte/"
    ),
    new FooterPair("Day.js", "https://day.js.org/en/"),
    new FooterPair("Download", "https://github.com/Level3Studios/bookz"),
  ];
</script>

<footer class="block py-4 mt-auto">
  <div class="container mx-auto px-4">
    <hr class="mb-4 border-b-1 border-slate-200" />
    <div class="flex flex-wrap items-center md:justify-between justify-center">
      <div class="w-full md:w-4/12 px-4">
        <div
          class="text-sm text-slate-500 font-semibold py-1 text-center md:text-left"
        >
          Copyright © {dateString} Bookz by
          <a
            href="https://www.level3studios.net"
            target="_blank"
            class="text-slate-500 hover:text-slate-700 text-sm font-semibold py-1"
          >
            Level 3 Studios
          </a>
        </div>
      </div>
      <div class="w-full md:w-8/12 px-4">
        <ul class="flex flex-wrap list-none md:justify-end justify-center">
          {#each footerLinks as link}
            <li>
              <a
                href={link.link}
                class="text-slate-600 hover:text-slate-800 text-sm font-semibold block py-1 px-3"
              >
                {link.label}
              </a>
            </li>
          {/each}
        </ul>
      </div>
    </div>
  </div>
</footer>
