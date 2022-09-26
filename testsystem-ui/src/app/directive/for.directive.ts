import {Directive, Input, TemplateRef, ViewContainerRef} from "@angular/core";

@Directive({ selector: '[for]'})
export class ForDirective {

  count: number;

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef) { }

  @Input('for')
  set setCount(count: number) {
    this.count = count;
    this.draw();
  }

  private draw(): void {
    if (this.viewContainer.length == this.count) {
      return;
    }

    if (this.count == 0) {
      this.viewContainer.clear();
      return;
    }

    if (this.viewContainer.length < this.count) {
      for(let i = this.viewContainer.length; i < this.count; i++) {
        this.viewContainer.createEmbeddedView(this.templateRef, new Context(i), i);
      }
    }

    if (this.viewContainer.length > this.count) {
      for (let i = this.count - 1; i < this.viewContainer.length; i++) {
        this.viewContainer.remove(i);
      }
    }
  }
}

export class Context {
  constructor(private index: number) { }
}
