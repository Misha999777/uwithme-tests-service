import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {EditTestComponent} from "./page/edit-test/edit-test.component";
import {EditQuestionComponent} from "./page/edit-question/edit-question.component";
import {ViewResultsComponent} from "./page/view-results/view-results.component";
import {StartComponent} from "./page/start-test/start.component";
import {TestSessionComponent} from "./page/test-session/test-session.component";
import {RedirectGuardService} from "./service/guard.service";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'test',
    pathMatch: 'full',
  },
  {
    path: 'test',
    component: EditTestComponent,
    canActivate: [RedirectGuardService]
  },
  {
    path: 'question',
    component: EditQuestionComponent,
    canActivate: [RedirectGuardService]
  },
  {
    path: 'result',
    component: ViewResultsComponent,
    canActivate: [RedirectGuardService]
  },
  {
    path: 'start',
    component: StartComponent,
    canActivate: [RedirectGuardService]
  },
  {
    path: 'session',
    component: TestSessionComponent,
    canActivate: [RedirectGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
