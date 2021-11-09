import {Footer} from '../containers/footer.container';
import {PublicHeader} from '../containers/publicHeader.container';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

export class AboutPageModel {


  constructor() {

  }

}

@Component({
  selector: 'about-page',
  templateUrl: './aboutPage.page.html',
  styleUrls: ['./aboutPage.page.scss']
})
export class AboutPage implements OnInit, AfterViewInit {
  @ViewChild(PublicHeader)
  private readonly headerElement: PublicHeader;
  @ViewChild(Footer)
  private readonly footerElement: Footer;
  model: AboutPageModel = new AboutPageModel();

  constructor(private readonly route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

