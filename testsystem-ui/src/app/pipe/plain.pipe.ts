import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: 'plain'
})
export class PlainPipe implements PipeTransform {

    transform(value: string): string {
        let div = document.createElement("div");
        div.innerHTML = value;
        return div.innerText;
    }
}