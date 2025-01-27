export const breakpoints = {
  large: '@media (min-width: 768px)',
  small: '@media (max-width: 767px)',
}

export const palette = {
  main: '#E45141',
  textPrimary: '#2C2C2C',
  textSecondary: '#5B5B5B',
  textDisablePlace: '#777777',
  divider: '#D9D9D9',
  shadow: 'rgba(0,0,0,10%)',
  dim: 'rgba(0,0,0,70%)',
  white: '#fff',
  yellow: '#FFE921',
}

export const typography = {
  // TODO : web, mobile 분기 - 최보성
  fontSize: {
    small: '12px',
    medium: '14px',
    large: '16px',
    xLarge: '18px',
  },
  fontWeight: {
    thin: 200,
    regular: 400,
    medium: 600,
    bold: 800,
  },
}

export const typographyDev = {
  web: {
    mainTitle: '2.6rem',
    subTitle: '2rem',
    content: '1.8rem',
    desc: '1.675rem',
  },
  mobile: {
    mainTitle: '2.2rem',
    subTitle: '1.8rem',
    content: '1.4rem',
    desc: '1.2rem',
  },
}

// margin
// TODO : %, px 확정 - 최보성
export const spacing = {
  // small: '4px',
  // medium: '8px',
  // large: '16px',
  // xLarge: '32px',
}

export const zindex = {
  base: 1,
  header: 10,
  modal: 20,
}
