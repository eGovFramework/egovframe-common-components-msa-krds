class CustomViewer extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: "open" });

        this.shadowRoot.innerHTML = `
            <style>
                :host {
                    display: block;
                    width: 100%;
                    height: auto;
                }
                iframe {
                    width: 100%;
                    border: none;
                    display: block;
                    overflow: hidden;
                }
            </style>
            <iframe></iframe>
        `;

        this.iframe = this.shadowRoot.querySelector("iframe");
        this.observer = null;
        this.resizeObserver = null;

        this.iframe.addEventListener("load", () => {
            this.adjustHeight();
            this.setupMutationObserver();
            this.setupResizeObserver();
        });

        window.addEventListener("resize", () => this.adjustHeight());
    }

    static get observedAttributes() {
        return ["src"];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === "src" && newValue) {
            this.iframe.src = newValue;
        }
    }

    setupMutationObserver() {
        try {
            const iframeDoc = this.iframe.contentDocument || this.iframe.contentWindow.document;
            if (!iframeDoc) return;

            if (this.observer) {
                this.observer.disconnect();
            }

            const targetNode = iframeDoc.body;
            if (!targetNode) return;

            this.observer = new MutationObserver(() => this.adjustHeight());

            this.observer.observe(targetNode, {
                childList: true,
                subtree: true,
                attributes: true,
                characterData: true
            });

            this.adjustHeight();
        } catch (error) {
            console.warn("Cannot setup MutationObserver:", error);
        }
    }

    setupResizeObserver() {
        try {
            const iframeDoc = this.iframe.contentDocument || this.iframe.contentWindow.document;
            const container = iframeDoc.querySelector("#container");

            if (!container || typeof ResizeObserver === "undefined") return;

            this.resizeObserver?.disconnect?.();

            this.resizeObserver = new ResizeObserver(() => {
                this.adjustHeight();
            });

            this.resizeObserver.observe(container);
        } catch (error) {
            console.warn("Cannot setup ResizeObserver:", error);
        }
    }

    adjustHeight() {
        try {
            const iframeDoc = this.iframe.contentDocument || this.iframe.contentWindow.document;
            if (!iframeDoc) return;

            setTimeout(() => {
                const container = iframeDoc.querySelector("#container");
                const contHeight = container?.scrollHeight || iframeDoc.body.scrollHeight || 0;
                const buffer = 2;
                const finalHeight = contHeight + buffer;

                const mainContainer = document.querySelector('.main-container');
                if (mainContainer) {
                    mainContainer.style.height = finalHeight + "px";
                }

                this.style.height = finalHeight + "px";
                this.iframe.style.height = finalHeight + "px";
            }, 150);
        } catch (error) {
            console.warn("Cannot adjust iframe height:", error);
        }
    }
}

customElements.define("custom-viewer", CustomViewer);
