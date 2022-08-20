import {Directive, Input, TemplateRef, ViewContainerRef} from "@angular/core";

@Directive({ selector: '[for]'})
export class ForDirective {

  count: number;

  constructor( private templateRef: TemplateRef<any>,
               private viewContainer: ViewContainerRef) { }

  @Input('for')
  set setCount(count: number) {
    this.count = count;
    this.draw();
  }

  private draw(): void {
    this.viewContainer.clear();

    for(let i = 0; i < this.count; i++) {
      this.viewContainer.createEmbeddedView(this.templateRef, new Context(i));
    }
  }
}

export class Context {
  constructor(private index: number) { }
}
