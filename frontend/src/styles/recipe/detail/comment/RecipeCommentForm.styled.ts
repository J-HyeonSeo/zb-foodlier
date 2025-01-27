// /* eslint-disable import/prefer-default-export */
import styled from 'styled-components'
import { palette } from '../../../../constants/Styles'
// import { palette, breakpoints } from '../../../../constants/Styles'

export const CommentForm = styled.div`
  width: 100%;
  display: flex;

  background: ${palette.white};
  border-radius: 1rem;
  height: 7rem;
`

export const CommentInput = styled.input`
  flex: 1 1 auto;
  padding-left: 2rem;
  border-radius: 1rem 0 0 1rem;
  border: 0.1rem solid ${palette.divider};
  border-right: 0.1rem solid ${palette.white};
`

export const CommentSubmit = styled.button`
  background-color: ${palette.main};
  border-radius: 0 1rem 1rem 0;
  width: 20%;
  max-width: 10rem;
  color: ${palette.white};
  font-weight: 600;
  font-size: 1.6rem;
`
