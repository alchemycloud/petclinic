import {UserRole} from '../../../services/administration/enums';
import {SessionService} from '../../../services/session.service';
import {Footer} from '../containers/footer.container';
import {PrivateHeader} from '../containers/privateHeader.container';
import {PetCreateForm} from '../forms/petCreateForm.form';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

export class PetCreatePageModel {


  constructor() {

  }

}

@Component({
  selector: 'pet-create-page',
  templateUrl: './petCreatePage.page.html',
  styleUrls: ['./petCreatePage.page.scss']
})
export class PetCreatePage implements OnInit, AfterViewInit {
  @ViewChild(PrivateHeader)
  private readonly headerElement: PrivateHeader;
  @ViewChild(PetCreateForm)
  private readonly petCreateFormElement: PetCreateForm;
  @ViewChild(Footer)
  private readonly footerElement: Footer;
  model: PetCreatePageModel = new PetCreatePageModel();

  constructor(private readonly route: ActivatedRoute, private readonly sessionService: SessionService, private readonly router: Router) {
    if (!([UserRole.VET].includes(sessionService.getSessionData().role)))
      this.router.navigate(['/welcome-page']);
  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

