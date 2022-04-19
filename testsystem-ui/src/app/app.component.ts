import {Component} from '@angular/core';
import {AuthServiceFactory} from "./service/auth.service.factory";
import {Store} from "@ngrx/store";
import {TestSession} from "./model/TestSession";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

}
